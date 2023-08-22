package com.github.miracle.klaytn.hackathon.contracts.impl.klaytn;

import com.klaytn.caver.Caver;
import com.klaytn.caver.contract.Contract;
import com.klaytn.caver.kct.kip7.KIP7;

import java.io.IOException;

public class KIP7Extension extends KIP7 {

    private final Contract extension;

    // Methods other than KIP7 methods should use extension to invoke
    public KIP7Extension(Caver caver, String abi, String contractAddress) throws IOException {
        super(caver, contractAddress);
        extension = Contract.create(caver, abi, contractAddress);
    }


}
