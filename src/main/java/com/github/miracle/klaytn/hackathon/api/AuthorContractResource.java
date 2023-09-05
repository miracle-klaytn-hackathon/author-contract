package com.github.miracle.klaytn.hackathon.api;

import com.github.miracle.klaytn.hackathon.contracts.ContractStore;
import com.github.miracle.klaytn.hackathon.contracts.FungibleContract;
import com.github.miracle.klaytn.hackathon.openapi.api.AuthorContractApi;
import com.github.miracle.klaytn.hackathon.openapi.model.*;
import com.github.miracle.klaytn.hackathon.utils.UniUtils;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

@ApplicationScoped
public class AuthorContractResource implements AuthorContractApi {

    private static final String CONTRACT_NAME = "AuthorContract";

    private final FungibleContract contract;

    private final String address;

    @Inject
    public AuthorContractResource(
            @ConfigProperty(name = "contract.author.address") String contractAddress,
            ContractStore contractStore) {
        contract = contractStore.getAuthorContract(contractAddress);
        address = contractAddress;
    }

    @Override
    public CompletionStage<Response> getAuthorContract() {
        return Uni.combine()
                .all()
                .unis(buildInfoUnis())
                .usingConcurrencyOf(3)
                .combinedWith(this::toSmartContract)
                .map(entity -> Response.ok(entity).build())
                .subscribeAsCompletionStage();
    }

    private List<Uni<?>> buildInfoUnis() {
        return List.of(
                UniUtils.createFromSupplier(contract::getSymbol),
                UniUtils.createFromSupplier(contract::getOwnerAddress));
    }

    private SmartContract toSmartContract(List<?> unis) {
        return SmartContract.builder()
                .name(contract.getName())
                .address(address)
                .symbol((String) unis.get(0))
                .owner((String) unis.get(1))
                .build();
    }

    @Override
    public CompletionStage<Response> getTotalToken() {
        return UniUtils.createFromSupplier(contract::getTotalSupply)
                .map(this::toTotalToken)
                .map(entity -> Response.ok(entity).build())
                .subscribeAsCompletionStage();
    }

    private TotalToken toTotalToken(BigInteger amount) {
        return TotalToken.builder()
                .contractName(contract.getName())
                .contractAddress(address)
                .amount(BigDecimal.valueOf(amount.longValue()))
                .build();
    }

    @Override
    public CompletionStage<Response> getAccountBalance(String accountAddress) {
        return UniUtils.createFromSupplier(() -> contract.getAccountBalance(accountAddress))
                .map(amount -> toAccountBalance(amount, accountAddress))
                .map(entity -> Response.ok(entity).build())
                .subscribeAsCompletionStage();
    }

    private AccountBalance toAccountBalance(Integer amount, String accountAddress) {
        return AccountBalance.builder()
                .contractAddress(address)
                .accountAddress(accountAddress)
                .balance(amount)
                .build();
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
