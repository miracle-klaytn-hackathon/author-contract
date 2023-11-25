package com.github.miracle.klaytn.hackathon.api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.github.miracle.klaytn.hackathon.openapi.api.SigninWithEthereumApi;
import com.github.miracle.klaytn.hackathon.openapi.model.ErrorResponse;
import com.github.miracle.klaytn.hackathon.openapi.model.SiweNonce;
import com.github.miracle.klaytn.hackathon.openapi.model.SiweRequest;
import com.github.miracle.klaytn.hackathon.openapi.model.SiweResponse;
import com.github.miracle.klaytn.hackathon.utils.EncryptionUtils;
import com.moonstoneid.siwe.SiweMessage;
import com.moonstoneid.siwe.error.SiweException;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class SigninWithEthereumResource implements SigninWithEthereumApi {

    private static final int NONCE_LENGTH = 16;

    @Inject
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String jwtIssuer;

    @Override
    public CompletionStage<Response> getNonce() {
        return Uni.createFrom().item(() -> EncryptionUtils.generateNonce(NONCE_LENGTH))
                .map(nonce -> new SiweNonce().nonce(nonce))
                .map(siweNonce -> Response.ok(siweNonce).build())
                .subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Response> verifySiweMessage(SiweRequest siweRequest) {
        return Uni.createFrom()
                .item(() -> verifySiweMessage(siweRequest.getMessage(), siweRequest.getSignature()))
                .map(this::buildJwt)
                .map(jwt -> Response.ok(new SiweResponse().token(jwt)).build())
                .onFailure()
                .recoverWithItem(exception -> Response.status(400)
                        .entity(new ErrorResponse().message(exception.getMessage())).build())
                .subscribeAsCompletionStage();
    }

    private String buildJwt(SiweMessage message) {
        return Jwt.issuer(jwtIssuer)
                .upn(message.getAddress())
                .issuedAt(parseISODateTime(message.getIssuedAt()))
                .expiresAt(parseISODateTime(message.getExpirationTime()))
                .groups(Set.of("user"))
                .sign();
    }

    private long parseISODateTime(String isoDateTime) {
        if (isoDateTime == null) {
            return 0;
        }
        LocalDateTime time = LocalDateTime.parse(isoDateTime, DateTimeFormatter.ISO_DATE_TIME);
        ZonedDateTime zdt = ZonedDateTime.of(time, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    private SiweMessage verifySiweMessage(String message, String signature) {
        try {
            SiweMessage.Parser parser = new SiweMessage.Parser();
            SiweMessage siweMessage = parser.parse(message);
            siweMessage.verify("localhost", siweMessage.getNonce(), signature);
            return siweMessage;
        } catch (SiweException siweException) {
            throw new RuntimeException("Siwe Verification failed!", siweException);
        }
    }

    @GET
    @Path("/user")
    @RolesAllowed("user")
    public String hello() {
        return "Hello User";
    }

}
