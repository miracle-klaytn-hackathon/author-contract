package com.github.miracle.klaytn.hackathon.contracts;

import com.klaytn.caver.abi.datatypes.Type;
import com.klaytn.caver.contract.Contract;

import java.util.List;

@SuppressWarnings("rawtypes")
public class AuthorContract  {

    private String ownerAddress;

    public AuthorContract(Contract contract) {
        try {
            this.ownerAddress = getOwner(contract);
        } catch (Exception | SmartContractException e) {
            System.out.println("Owner not found in deployed contract. " + e.getMessage());
        }
    }

    public String getOwner() {
        return ownerAddress;
    }

    private static String getOwner(Contract contract) throws Exception, SmartContractException {
        List<Type> ownerOnChain = contract.call("owner");
        if (ownerOnChain.size() != 1)
            throw new SmartContractException("This contract does not have one owner");
        Object ownerAddress = ownerOnChain.get(0).getValue();
        if (!(ownerAddress instanceof String))
            throw new SmartContractException("Incorrect owner type");
        return (String) ownerAddress;
    }

}
