package com.github.miracle.klaytn.hackathon.api;


import com.moonstoneid.siwe.SiweMessage;
import com.moonstoneid.siwe.error.SiweException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;


@Path("/auth")
@ApplicationScoped
public class AuthResource {

    @Path("/nonce")
    public String nonce() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] random = new byte[24];
        secureRandom.nextBytes(random);
        return Base64.getEncoder().encodeToString(random);
    }

    @Path("/verify")
    public String verify(@RequestBody Map<String, String> requestBody) throws SiweException {
        SiweMessage siwe = new SiweMessage.Parser().parse(requestBody.get("siwe-message"));
        siwe.verify("localhost", siwe.getNonce(), requestBody.get("siwe-sign"));
        return Jwt.issuer("https://localhost")
                .upn(siwe.getAddress())
                .issuedAt(Long.parseLong(siwe.getIssuedAt()))
                .expiresAt(Long.parseLong(siwe.getExpirationTime()))
                .sign();
    }

    @Path("/invalidate")
    public Response invalidate() {
        return null;
    }

    @Path("/echo-jwt")
    public String echoJwt(
            @HeaderParam("Authorization") String jwt
    ) {
        return jwt;
    }

}
