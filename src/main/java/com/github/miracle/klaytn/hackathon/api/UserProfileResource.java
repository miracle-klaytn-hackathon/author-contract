package com.github.miracle.klaytn.hackathon.api;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.github.miracle.klaytn.hackathon.openapi.api.UserProfileApi;
import com.github.miracle.klaytn.hackathon.openapi.model.ErrorResponse;
import com.github.miracle.klaytn.hackathon.openapi.model.UserProfile;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public class UserProfileResource implements UserProfileApi {

    @Context
    private SecurityContext securityContext;

    @Override
    @RolesAllowed({ "user", "author", "admin" })
    public CompletionStage<Response> createUserProfile(UserProfile userProfile) {
        throw new UnsupportedOperationException("Unimplemented method 'createUserProfile'");
    }

    @Override
    @RolesAllowed({ "user", "author", "admin" })
    public CompletionStage<Response> getUserProfile(String address) {
        if (!securityContext.getUserPrincipal().getName().equals(address)) {
            return CompletableFuture.completedStage(
                    Response.status(401).entity(
                                new ErrorResponse()
                                    .code("401")
                                    .message("Cannot request another user profile"))
                            .build());
        }
        throw new UnsupportedOperationException("Unimplemented method 'getProfile'");
    }

}
