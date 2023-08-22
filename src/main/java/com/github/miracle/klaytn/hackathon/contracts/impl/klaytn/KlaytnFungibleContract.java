package com.github.miracle.klaytn.hackathon.contracts.impl.klaytn;

import com.klaytn.caver.contract.SendOptions;
import com.klaytn.caver.kct.kip7.KIP7;
import com.klaytn.caver.methods.response.TransactionReceipt;
import com.klaytn.caver.wallet.KeyringContainer;
import com.klaytn.caver.wallet.keyring.AbstractKeyring;

import java.math.BigInteger;

public abstract class KlaytnFungibleContract {

    protected TransactionReceipt.TransactionReceiptData
    mint(KIP7 contract, AbstractKeyring minterKeyring, BigInteger amount) throws Exception {
        String minterAddress = minterKeyring.getAddress().toLowerCase();
        KeyringContainer wallet = contract.getCaver().wallet;
        if (wallet.getKeyring(minterAddress) == null) {
            wallet.add(minterKeyring);
        }
        SendOptions defaultSendOptions = new SendOptions();
        defaultSendOptions.setFrom(minterKeyring.getAddress());
        contract.setDefaultSendOptions(defaultSendOptions);
        TransactionReceipt.TransactionReceiptData receiptData = contract.mint(minterAddress, amount);
        wallet.remove(minterAddress);
        if ("0".equals(receiptData.getStatus())) {
            throw new Exception("On Chain Transaction error");
        }
        return receiptData;
    }

}
