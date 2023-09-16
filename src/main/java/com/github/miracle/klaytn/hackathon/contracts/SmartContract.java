package com.github.miracle.klaytn.hackathon.contracts;

public interface SmartContract {

    String getName() throws SmartContractException;

    String getAddress() throws SmartContractException;

    String getSymbol() throws SmartContractException;

    String getOwnerAddress() throws SmartContractException;

}
