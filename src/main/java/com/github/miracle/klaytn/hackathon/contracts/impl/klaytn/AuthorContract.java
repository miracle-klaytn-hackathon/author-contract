package com.github.miracle.klaytn.hackathon.contracts.impl.klaytn;

import com.github.miracle.klaytn.hackathon.contracts.AuthorContractOnChain;
import com.github.miracle.klaytn.hackathon.contracts.SmartContractException;
import com.github.miracle.klaytn.hackathon.openapi.model.MintReceipt;
import com.github.miracle.klaytn.hackathon.utils.ContractUtils;
import com.klaytn.caver.contract.SendOptions;
import com.klaytn.caver.kct.kip7.KIP7;
import com.klaytn.caver.methods.response.TransactionReceipt;
import com.klaytn.caver.wallet.keyring.AbstractKeyring;
import com.klaytn.caver.wallet.keyring.KeyringFactory;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.math.BigInteger;

@ApplicationScoped
public class AuthorContract implements AuthorContractOnChain {

    @Inject
    @ConfigProperty(name = "CONTRACT_AUTHOR_ADDRESS")
    private String contractAddress;

    @Inject
    @ConfigProperty(name = "MIRACLE_PRIVATE_KEY")
    private String miraclePrivateKey;

    @Inject
    // TODO: add this environment variable
    @ConfigProperty(name = "MIRACLE_ADDRESS")
    private String miracleAddress;

    @Inject
    private CaverProvider caverProvider;

    private KIP7 onChainContract;

    @PostConstruct
    public void init() throws Exception {
        AbstractKeyring miracleKeyRing = KeyringFactory.createFromPrivateKey(miraclePrivateKey);
        String contractAbi = ContractUtils.loadContractAbi("AuthorContract");
        onChainContract = new KIP7(caverProvider.get(), contractAddress);
        // TODO: force use contractAbi by reflection
        SendOptions defaultSendOptions = new SendOptions();
        defaultSendOptions.setFrom(miracleKeyRing.getAddress());
        onChainContract.setDefaultSendOptions(defaultSendOptions);
    }

    @Override
    public MintReceipt mint(BigInteger amount) throws SmartContractException {
        AbstractKeyring miracleKeyRing = KeyringFactory.createFromPrivateKey(miraclePrivateKey);
        onChainContract.getCaver().wallet.add(miracleKeyRing);
        try {
            TransactionReceipt.TransactionReceiptData receipt = onChainContract.mint(
                    miracleAddress, amount);
            if (receipt.getTxError() != null) {
                throw new Exception("On Chain Transaction error");
            }
            return ContractUtils.toMintReceipt(receipt);
        } catch (Exception e) {
            throw new SmartContractException("Cannot mint. ", e);
        }
    }

}
