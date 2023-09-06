package com.github.miracle.klaytn.hackathon.api;

import com.github.miracle.klaytn.hackathon.contracts.ContractStore;
import com.github.miracle.klaytn.hackathon.contracts.NonFungibleContract;
import com.github.miracle.klaytn.hackathon.entities.BookTokenRecommendation;
import com.github.miracle.klaytn.hackathon.openapi.api.BookContractApi;
import com.github.miracle.klaytn.hackathon.openapi.model.SmartContract;
import com.github.miracle.klaytn.hackathon.utils.UniUtils;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class BookTokenResource implements BookContractApi {

    @Inject
    ContractStore contractStore;

    @Inject
    BookTokenRecommendation tokenRecommendation;

    @Override
    public CompletionStage<Response> getContractInfo(String address) {
        NonFungibleContract bookContract = contractStore.getBookContract(address);
        return Uni.combine()
                .all()
                .unis(buildInfoUnis(bookContract))
                .usingConcurrencyOf(3)
                .combinedWith(this::toSmartContract)
                .map(entity -> Response.ok(entity).build())
                .subscribeAsCompletionStage();
    }

    private List<Uni<?>> buildInfoUnis(NonFungibleContract contract) {
        return List.of(
                UniUtils.createFromSupplier(contract::getName),
                UniUtils.createFromSupplier(contract::getAddress),
                UniUtils.createFromSupplier(contract::getAbi),
                UniUtils.createFromSupplier(contract::getSymbol),
                UniUtils.createFromSupplier(contract::getOwnerAddress));
    }

    private SmartContract toSmartContract(List<?> unis) {
        return SmartContract.builder()
                .name((String) unis.get(0))
                .address((String) unis.get(1))
                .abi((String) unis.get(2))
                .symbol((String) unis.get(3))
                .owner((String) unis.get(4))
                .build();
    }

    @Override
    public CompletionStage<Response> getRecommendation() {
        return null;
    }

}
