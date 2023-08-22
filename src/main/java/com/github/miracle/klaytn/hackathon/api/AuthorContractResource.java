package com.github.miracle.klaytn.hackathon.api;

import com.github.miracle.klaytn.hackathon.contracts.FungibleContract;
import com.github.miracle.klaytn.hackathon.openapi.api.AuthorContractApi;
import com.github.miracle.klaytn.hackathon.openapi.model.MintFTRequest;
import com.github.miracle.klaytn.hackathon.openapi.model.MintReceipt;
import com.github.miracle.klaytn.hackathon.utils.UniUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.math.BigInteger;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

@ApplicationScoped
public class AuthorContractResource implements AuthorContractApi {

    @Inject
    private FungibleContract fungibleContract;

    @Override
    public CompletionStage<Response> getAuthorContract() {
        return null;
    }

    @Override
    public CompletionStage<Response> mintAuthorToken(MintFTRequest mintFTRequest) {
        Supplier<MintReceipt> mintSupplier = () -> fungibleContract.mint(
                mintFTRequest.getPrivateKey(),
                BigInteger.valueOf(mintFTRequest.getAmount()));
        return  UniUtils.createFromSupplier(mintSupplier)
                .map(receipt -> Response.ok().entity(receipt).build())
                .subscribeAsCompletionStage();
    }

}
