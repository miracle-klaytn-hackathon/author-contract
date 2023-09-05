package com.github.miracle.klaytn.hackathon.contracts;

public interface ContractStore {

    FungibleContract getAuthorContract(String address) throws SmartContractException;

    NonFungibleContract getBookContract(String address) throws SmartContractException;

}
