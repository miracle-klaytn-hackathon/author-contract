package com.github.miracle.klaytn.hackathon.api;

import com.github.miracle.klaytn.hackathon.contracts.AuthorContractOnChain;
import com.github.miracle.klaytn.hackathon.openapi.api.AuthorContractApi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.math.BigInteger;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class AuthorContractResource implements AuthorContractApi {

    @Inject
    private AuthorContractOnChain authorContract;

    @Override
    public CompletionStage<Response> getAuthorContract() {
        return null;
    }

    @Override
    public CompletionStage<Response> mintAuthorToken() {
        return Uni.createFrom()
                .item(() -> authorContract.mint(BigInteger.valueOf(100000)))
                .map(receipt -> Response.ok().entity(receipt).build())
                .subscribeAsCompletionStage();
    }

}
