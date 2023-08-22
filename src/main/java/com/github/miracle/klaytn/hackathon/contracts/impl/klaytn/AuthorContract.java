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
    @ConfigProperty(name = "contract.author.address")
    private String contractAddress;

    @Inject
    @ConfigProperty(name = "admin.key")
    private String adminKey;

    @Inject
    @ConfigProperty(name = "admin.address")
    private String adminAddress;

    @Inject
    private CaverProvider caverProvider;

    private KIP7 onChainContract;

    @PostConstruct
    public void init() throws Exception {
        AbstractKeyring adminKeyRing = KeyringFactory.createFromPrivateKey(adminKey);
        String contractAbi = ContractUtils.loadContractAbi("AuthorContract");
        onChainContract = new KIP7Extension(caverProvider.get(), contractAbi, contractAddress);
        SendOptions defaultSendOptions = new SendOptions();
        defaultSendOptions.setFrom(adminKeyRing.getAddress());
        onChainContract.setDefaultSendOptions(defaultSendOptions);
        onChainContract.getCaver().wallet.add(adminKeyRing);
    }

    @Override
    public MintReceipt mint(BigInteger amount) throws SmartContractException {
        try {
            TransactionReceipt.TransactionReceiptData receipt = onChainContract.mint(
                    adminAddress, amount);
            if ("0".equals(receipt.getStatus())) {
                throw new Exception("On Chain Transaction error");
            }
            return ContractUtils.toMintReceipt(receipt);
        } catch (Exception e) {
            throw new SmartContractException("Cannot mint. ", e);
        }
    }

}
