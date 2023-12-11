package com.github.miracle.klaytn.hackathon.api;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.github.miracle.klaytn.hackathon.entities.User;
import com.github.miracle.klaytn.hackathon.openapi.api.UserProfileApi;
import com.github.miracle.klaytn.hackathon.openapi.model.ErrorResponse;
import com.github.miracle.klaytn.hackathon.openapi.model.UserProfile;
import com.github.miracle.klaytn.hackathon.utils.mappers.UserMapper;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class UserProfileResource implements UserProfileApi {

    @Context
    SecurityContext securityContext;

    @Context
    UriInfo uriInfo;

    @Inject
    UserMapper userMapper;

    @Override
    @RolesAllowed({ "user", "author", "admin" })
    public CompletionStage<Response> createUserProfile(UserProfile userProfile) {
        return User.isUserNotExist(userProfile.getWalletAddress())
                .<User>map(ignored -> userMapper.toUser(userProfile))
                .<User>flatMap(user -> user.persist())
                .map(this::buildCreatedUserResponse)
                .onFailure()
                .recoverWithItem(this::buildBadRequestResponse)
                .subscribeAsCompletionStage();
    }

    private Response buildBadRequestResponse(Throwable throwable) {
        ErrorResponse errorResponse = new ErrorResponse().message(throwable.getMessage());
        return Response.status(Status.BAD_REQUEST)
                .entity(errorResponse)
                .build();
    }

    private Response buildCreatedUserResponse(User user) {
        URI createdLocation = uriInfo.getBaseUriBuilder()
                .path(UserProfileApi.class)
                .path(user.getWalletAddress())
                .build();
        return Response.created(createdLocation).build();
    }

    @Override
    @RolesAllowed({ "user", "author", "admin" })
    public CompletionStage<Response> getUserProfile(String address) {
        return User.findByWalletAddress(address)
                .map(userMapper::toUserProfile)
                .map(this::buildFindUserProfileResponse)
                .onFailure()
                .recoverWithItem(this::buildBadRequestResponse)
                .subscribeAsCompletionStage();
    }

    private Response buildFindUserProfileResponse(UserProfile userProfile) {
        return Response.ok(userProfile).build();
    }

}
