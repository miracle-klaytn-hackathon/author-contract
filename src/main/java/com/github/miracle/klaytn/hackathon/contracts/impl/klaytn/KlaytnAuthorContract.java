package com.github.miracle.klaytn.hackathon.contracts.impl.klaytn;

import com.github.miracle.klaytn.hackathon.contracts.FungibleContract;
import com.github.miracle.klaytn.hackathon.contracts.SmartContractException;
import com.github.miracle.klaytn.hackathon.openapi.model.MintReceipt;
import com.github.miracle.klaytn.hackathon.utils.ContractUtils;
import com.klaytn.caver.wallet.keyring.AbstractKeyring;
import com.klaytn.caver.wallet.keyring.KeyringFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.math.BigInteger;

@ApplicationScoped
public class KlaytnAuthorContract extends KlaytnFungibleContract implements FungibleContract {

    @Inject
    @ConfigProperty(name = "contract.author.address")
    private String contractAddress;

    @Override
    public MintReceipt mint(String privateKey, BigInteger amount) throws SmartContractException {
        AbstractKeyring keyring = KeyringFactory.createFromPrivateKey(privateKey);
        try {
            return ContractUtils.toMintReceipt(super.mint(contractAddress, keyring, amount));
        } catch (Exception e) {
            throw new SmartContractException("Cannot mint. ", e);
        }
    }

}
