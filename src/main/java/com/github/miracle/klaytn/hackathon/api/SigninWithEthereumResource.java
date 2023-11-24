package com.github.miracle.klaytn.hackathon.api;

import java.util.concurrent.CompletionStage;

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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class SigninWithEthereumResource implements SigninWithEthereumApi {

    private static final int NONCE_LENGTH = 16;

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
                .map(message -> Jwt.issuer("https://localhost")
                        .upn(message.getAddress())
                        .issuedAt(Long.parseLong(message.getIssuedAt()))
                        .expiresAt(Long.parseLong(message.getExpirationTime()))
                        .sign())
                .map(jwt -> Response.ok(new SiweResponse().token(jwt)).build())
                // .onFailure()
                // .recoverWithItem(exception -> Response.status(400).entity(new ErrorResponse().message(exception.getMessage())).build())
                .subscribeAsCompletionStage();
    }

    private SiweMessage verifySiweMessage(String message, String signature) {
        System.out.println(message);
        try {
            SiweMessage siweMessage = new SiweMessage.Parser().parse(message);
            siweMessage.verify("http://localhost", siweMessage.getNonce(), signature);
            return siweMessage;
        } catch (SiweException siweException) {
            throw new RuntimeException("Siwe Verification failed!", siweException);
        }
    }

}
