package com.github.miracle.klaytn.hackathon.contracts;

import com.github.miracle.klaytn.hackathon.openapi.model.MintReceipt;

import java.math.BigInteger;

public interface FungibleContract {

    String getName() throws SmartContractException;

    String getAddress() throws SmartContractException;

    String getSymbol() throws SmartContractException;

    String getOwnerAddress() throws SmartContractException;

    Integer getDecimals() throws SmartContractException;

    BigInteger getTotalSupply() throws SmartContractException;

    MintReceipt mint(String privateKey, BigInteger amount) throws SmartContractException;

}
