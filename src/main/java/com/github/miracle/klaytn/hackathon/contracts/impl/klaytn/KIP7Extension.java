package com.github.miracle.klaytn.hackathon.contracts.impl.klaytn;

import com.klaytn.caver.Caver;
import com.klaytn.caver.abi.datatypes.Type;
import com.klaytn.caver.contract.Contract;
import com.klaytn.caver.kct.kip7.KIP7;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@SuppressWarnings("rawtypes")
public class KIP7Extension extends KIP7 {

    private final Contract extension;

    // Methods other than KIP7 methods should use extension to invoke
    private KIP7Extension(Caver caver, String address, Contract extension) throws IOException {
        super(caver, address);
        this.extension = extension;
    }

    public static KIP7Extension create(Caver caver, String abi, String address) throws IOException {
        Contract extension = Contract.create(caver, abi, address);
        return new KIP7Extension(caver, address, extension);
    }

    public String owner() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Type> ownerOnChain = extension.call("owner");
        if (ownerOnChain.size() != 1)
            throw new IllegalAccessException("This contract does not have one owner");
        Object ownerAddress = ownerOnChain.get(0).getValue();
        if (!(ownerAddress instanceof String))
            throw new ClassNotFoundException("Incorrect owner type");
        return (String) ownerAddress;
    }

}
