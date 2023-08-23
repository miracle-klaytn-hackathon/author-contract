package com.github.miracle.klaytn.hackathon.contracts.impl.klaytn;

import com.github.miracle.klaytn.hackathon.contracts.ContractStore;
import com.github.miracle.klaytn.hackathon.contracts.FungibleContract;
import com.github.miracle.klaytn.hackathon.contracts.SmartContractException;
import com.github.miracle.klaytn.hackathon.utils.ContractUtils;
import com.klaytn.caver.Caver;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class KlaytnContractStore implements ContractStore {

    private final Map<String, ? super FungibleContract> STORAGE = new HashMap<>();

    private final Caver sharedCaver;

    public KlaytnContractStore(
            @ConfigProperty(name = "rpc.url") String rpcURL) {
        sharedCaver = new Caver(rpcURL);
    }

    @Override
    public FungibleContract getAuthorContract(String address, String name) throws SmartContractException {
        try {
            if (STORAGE.get(address) == null) {
                String contractAbi = ContractUtils.loadContractAbi(name);
                KIP7Extension contract = KIP7Extension.create(sharedCaver, contractAbi, address);
                KlaytnAuthorContract contractWrapper = new KlaytnAuthorContract(contract, name);
                STORAGE.put(address, contractWrapper);
            }
            return (FungibleContract) STORAGE.get(address);
        } catch (Exception exception) {
            throw new SmartContractException("Could not load contract abi of " + name, exception);
        }
    }

}
