package com.github.miracle.klaytn.hackathon.contracts;

import com.github.miracle.klaytn.hackathon.openapi.model.MintReceipt;

import java.math.BigInteger;

public interface FungibleContract {

    MintReceipt mint(String privateKey, BigInteger amount) throws SmartContractException;

}
