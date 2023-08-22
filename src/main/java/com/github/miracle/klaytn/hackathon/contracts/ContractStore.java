package com.github.miracle.klaytn.hackathon.contracts;

public interface ContractStore {

    FungibleContract getAuthorContract(String address, String name) throws SmartContractException;

}
