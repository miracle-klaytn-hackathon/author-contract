package com.github.miracle.klaytn.hackathon.contracts.impl.klaytn.wrapper;

import com.github.miracle.klaytn.hackathon.contracts.FungibleContract;
import com.github.miracle.klaytn.hackathon.contracts.SmartContractException;
import com.github.miracle.klaytn.hackathon.contracts.impl.klaytn.KIP7Extension;
import com.github.miracle.klaytn.hackathon.contracts.impl.klaytn.KlaytnFungibleContract;
import com.github.miracle.klaytn.hackathon.openapi.model.MintReceipt;
import com.github.miracle.klaytn.hackathon.utils.ContractUtils;
import com.klaytn.caver.wallet.keyring.AbstractKeyring;
import com.klaytn.caver.wallet.keyring.KeyringFactory;

import java.math.BigInteger;

public class KlaytnAuthorContract extends KlaytnFungibleContract implements FungibleContract {

    private final KIP7Extension onChainContract;

    private final String contractName;

    public KlaytnAuthorContract(KIP7Extension onChainContract, String contractName) {
        this.onChainContract = onChainContract;
        this.contractName = contractName;
    }

    @Override
    public String getName() throws SmartContractException {
        return contractName;
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
            throw new SmartContractException(
                    "Could not find a symbol for the given contract", exception);
        }
    }

    @Override
    public String getOwnerAddress() throws SmartContractException {
        try {
            return onChainContract.owner();
        } catch (Exception exception) {
            throw new SmartContractException(
                    "Could not find an owner for the given contract", exception);
        }
    }

    @Override
    public Integer getDecimals() throws SmartContractException {
        try {
            return onChainContract.decimals();
        } catch (Exception exception) {
            throw new SmartContractException(
                    "Could not find decimals for the given contract", exception);
        }
    }

    @Override
    public BigInteger getTotalSupply() throws SmartContractException {
        try {
            return onChainContract.totalSupply();
        } catch (Exception exception) {
            throw new SmartContractException(
                    "Could not get total supply for the given contract", exception);
        }
    }

    @Override
    public Integer getAccountBalance(String accountAddress) {
        try {
            return onChainContract.balanceOf(accountAddress).intValue();
        } catch (Exception exception) {
            throw new SmartContractException(
                    "Could not get total supply for the given contract", exception);
        }
    }

    @Override
    public MintReceipt mint(String privateKey, BigInteger amount) throws SmartContractException {
        AbstractKeyring keyring = KeyringFactory.createFromPrivateKey(privateKey);
        try {
            return ContractUtils.toMintReceipt(super.mint(onChainContract, keyring, amount));
        } catch (Exception exception) {
            throw new SmartContractException("Cannot mint. ", exception);
        }
    }

}
