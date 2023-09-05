package com.github.miracle.klaytn.hackathon.contracts;

import com.github.miracle.klaytn.hackathon.openapi.model.MintReceipt;

import java.math.BigInteger;

public interface FungibleContract extends SmartContract {

    Integer getDecimals() throws SmartContractException;

    BigInteger getTotalSupply() throws SmartContractException;

    Integer getAccountBalance(String accountAddress);

    MintReceipt mint(String privateKey, BigInteger amount) throws SmartContractException;

}
