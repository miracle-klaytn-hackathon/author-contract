package com.github.miracle.klaytn.hackathon.contracts;

import java.io.IOException;
import java.net.URISyntaxException;

import com.github.miracle.klaytn.hackathon.utils.ContractUtils;

public interface ContractStore {

    default String getBookContractABI() {
        try {
            return ContractUtils.loadContractAbi("BookContract");
        } catch (URISyntaxException | IOException e) {
            throw new SmartContractException(e.getMessage(), e);
        }
    }

    FungibleContract getAuthorContract(String address) throws SmartContractException;

    NonFungibleContract getBookContract(String address) throws SmartContractException;

}
