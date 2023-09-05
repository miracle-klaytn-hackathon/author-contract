package com.github.miracle.klaytn.hackathon.contracts.impl.klaytn;

import com.github.miracle.klaytn.hackathon.contracts.*;
import com.github.miracle.klaytn.hackathon.contracts.impl.klaytn.wrapper.KlaytnAuthorContract;
import com.github.miracle.klaytn.hackathon.contracts.impl.klaytn.wrapper.KlaytnBookContract;
import com.github.miracle.klaytn.hackathon.utils.ContractUtils;
import com.klaytn.caver.Caver;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class KlaytnContractStore implements ContractStore {

    private final Map<String, ? super SmartContract> STORAGE = new HashMap<>();

    private static final String AUTHOR_CONTRACT_NAME = "AuthorToken";

    private static final String BOOK_CONTRACT_NAME = "BookToken";

    private final Caver sharedCaver;

    public KlaytnContractStore(
            @ConfigProperty(name = "rpc.url") String rpcURL) {
        sharedCaver = new Caver(rpcURL);
    }

    @Override
    public FungibleContract getAuthorContract(String address) throws SmartContractException {
        try {
            if (STORAGE.get(address) == null) {
                String contractAbi = ContractUtils.loadContractAbi(AUTHOR_CONTRACT_NAME);
                KIP7Extension contract = KIP7Extension.create(sharedCaver, contractAbi, address);
                KlaytnAuthorContract contractWrapper = new KlaytnAuthorContract(contract, AUTHOR_CONTRACT_NAME);
                STORAGE.put(address, contractWrapper);
            }
            return (FungibleContract) STORAGE.get(address);
        } catch (Exception exception) {
            throw new SmartContractException(exception.getMessage(), exception);
        }
    }

    @Override
    public NonFungibleContract getBookContract(String address) throws SmartContractException {
        try {
            if (STORAGE.get(address) == null) {
                String contractAbi = ContractUtils.loadContractAbi(BOOK_CONTRACT_NAME);
                KIP17Extension contract = KIP17Extension.create(sharedCaver, contractAbi, address);
                KlaytnBookContract contractWrapper = new KlaytnBookContract(contract);
                STORAGE.put(address, contractWrapper);
            }
            return (NonFungibleContract) STORAGE.get(address);
        } catch (Exception exception) {
            throw new SmartContractException(exception.getMessage(), exception);
        }
    }

}
