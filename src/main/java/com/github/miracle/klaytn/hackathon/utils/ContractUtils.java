package com.github.miracle.klaytn.hackathon.utils;

import com.github.miracle.klaytn.hackathon.openapi.model.MintReceipt;
import com.klaytn.caver.methods.response.TransactionReceipt;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class ContractUtils {

    private static final String BAOBAB_TX_IO = "";

    private ContractUtils() {
    }

    public static String loadContractAbi(String contractName) throws URISyntaxException, IOException {
        ClassLoader classLoader = ContractUtils.class.getClassLoader();
        Path contractPath = Path.of("abi", contractName.concat(".abi"));
        URL contractUrl = classLoader.getResource(contractPath.toString());
        assert contractUrl != null;
        return Files.readString(Path.of(contractUrl.toURI()));
    }

    public static MintReceipt toMintReceipt(TransactionReceipt.TransactionReceiptData transactionReceipt) {
        return MintReceipt.builder()
                .minterAddress(transactionReceipt.getFrom())
                .tokenAddress(transactionReceipt.getContractAddress())
                .txHash(transactionReceipt.getTransactionHash())
                .txBlockNumber(Integer.valueOf(transactionReceipt.getBlockNumber()))
                .txBlockHash(transactionReceipt.getBlockHash())
                .gasCost(Integer.valueOf(transactionReceipt.getGasUsed()))
                .ref(BAOBAB_TX_IO + transactionReceipt.getTransactionHash())
                .build();
    }

}
