package com.github.miracle.klaytn.hackathon.api;

import com.github.miracle.klaytn.hackathon.contracts.ContractStore;
import com.github.miracle.klaytn.hackathon.contracts.FungibleContract;
import com.github.miracle.klaytn.hackathon.openapi.api.AuthorContractApi;
import com.github.miracle.klaytn.hackathon.openapi.model.MintFTRequest;
import com.github.miracle.klaytn.hackathon.openapi.model.MintReceipt;
import com.github.miracle.klaytn.hackathon.openapi.model.SmartContract;
import com.github.miracle.klaytn.hackathon.openapi.model.TotalToken;
import com.github.miracle.klaytn.hackathon.utils.UniUtils;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

@ApplicationScoped
public class AuthorContractResource implements AuthorContractApi {

    private static final String CONTRACT_NAME = "AuthorContract";

    private final FungibleContract contract;

    @Inject
    public AuthorContractResource(
            @ConfigProperty(name = "contract.author.address") String address,
            ContractStore klaytnContractStore) {
        contract = klaytnContractStore.getAuthorContract(address, CONTRACT_NAME);
    }

    @Override
    public CompletionStage<Response> getAuthorContract() {
        return Uni.combine()
                .all()
                .unis(buildInfoUnis())
                .usingConcurrencyOf(5)
                .combinedWith(AuthorContractResource::toSmartContractResponse)
                .subscribeAsCompletionStage();
    }

    private List<Uni<?>> buildInfoUnis() {
        return List.of(
                UniUtils.createFromSupplier(contract::getName),
                UniUtils.createFromSupplier(contract::getSymbol),
                UniUtils.createFromSupplier(contract::getAddress),
                UniUtils.createFromSupplier(contract::getOwnerAddress),
                UniUtils.createFromSupplier(contract::getDecimals));
    }

    private static Response toSmartContractResponse(List<?> unis) {
        SmartContract authorContract = SmartContract.builder()
                .name((String) unis.get(0))
                .symbol((String) unis.get(1))
                .address((String) unis.get(2))
                .owner((String) unis.get(3))
                .decimals((Integer) unis.get(4))
                .build();
        return Response.ok().entity(authorContract).build();
    }

    @Override
    public CompletionStage<Response> getTotalToken() {
        return null;
    }

    @Override
    public CompletionStage<Response> getAccountBalance(String accountAddress) {
        return null;
    }

    @Override
    public CompletionStage<Response> mintAuthorToken(MintFTRequest mintFTRequest) {
        Supplier<MintReceipt> mintSupplier = () -> contract.mint(
                mintFTRequest.getPrivateKey(),
                BigInteger.valueOf(mintFTRequest.getAmount()));
        return UniUtils.createFromSupplier(mintSupplier)
                .map(receipt -> Response.ok().entity(receipt).build())
                .subscribeAsCompletionStage();
    }

}
