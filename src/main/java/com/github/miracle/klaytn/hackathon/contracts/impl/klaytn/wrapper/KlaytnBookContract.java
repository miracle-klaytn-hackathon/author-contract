package com.github.miracle.klaytn.hackathon.contracts.impl.klaytn.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.miracle.klaytn.hackathon.contracts.NonFungibleContract;
import com.github.miracle.klaytn.hackathon.contracts.SmartContractException;
import com.github.miracle.klaytn.hackathon.contracts.impl.klaytn.KIP17Extension;

public class KlaytnBookContract implements NonFungibleContract {

    private final KIP17Extension onChainContract;

    private final ObjectMapper objectMapper;

    public KlaytnBookContract(KIP17Extension onChainContract) {
        this.onChainContract = onChainContract;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String getName() throws SmartContractException {
        try {
            return onChainContract.name();
        } catch (Exception exception) {
            throw new SmartContractException(exception.getMessage(), exception);
        }
    }

    @Override
    public String getAddress() throws SmartContractException {
        return onChainContract.getContractAddress();
    }

    @Override
    public String getSymbol() throws SmartContractException {
        try {
            return onChainContract.symbol();
        } catch (Exception exception) {
            throw new SmartContractException(exception.getMessage(), exception);
        }
    }

    @Override
    public String getOwnerAddress() throws SmartContractException {
        try {
            return onChainContract.owner();
        } catch (Exception exception) {
            throw new SmartContractException(exception.getMessage(), exception);
        }
    }

    @Override
    public String getAbi() {
        return onChainContract.getAbi();
    }

}
