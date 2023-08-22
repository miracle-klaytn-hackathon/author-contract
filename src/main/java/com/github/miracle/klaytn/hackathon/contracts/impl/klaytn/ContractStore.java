package com.github.miracle.klaytn.hackathon.contracts.impl.klaytn;

import com.github.miracle.klaytn.hackathon.utils.ContractUtils;
import com.klaytn.caver.Caver;
import com.klaytn.caver.contract.Contract;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class ContractStore {

    private final Map<String, ? super Contract> STORAGE = new HashMap<>();

    private final Caver sharedCaver;

    public ContractStore(
            @ConfigProperty(name = "rpc.url") String rpcURL) {
        sharedCaver = new Caver(rpcURL);
    }

    public KIP7Extension getFungibleContract(String address, String name) throws Exception {
        if (STORAGE.get(address) == null) {
            String contractAbi = ContractUtils.loadContractAbi(name);
            KIP7Extension contract = KIP7Extension.create(sharedCaver, contractAbi, address);
            STORAGE.put(address, contract);
        }
        return (KIP7Extension) STORAGE.get(address);
    }

}
