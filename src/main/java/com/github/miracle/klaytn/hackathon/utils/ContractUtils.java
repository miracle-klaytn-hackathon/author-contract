package com.github.miracle.klaytn.hackathon.utils;

import com.github.miracle.klaytn.hackathon.openapi.model.MintReceipt;
import com.klaytn.caver.methods.response.TransactionReceipt;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ContractUtils {

    private static final String BAOBAB_TX_IO = "";

    private ContractUtils() {
    }

    public static String loadContractAbi(String contractName) throws URISyntaxException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Path contractAbiPath = Path.of("abi", contractName.concat(".abi"));
        try (InputStream contractAbi = classLoader.getResourceAsStream(contractAbiPath.toString())) {
            assert contractAbi != null;
            return new String(contractAbi.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public static MintReceipt toMintReceipt(
            TransactionReceipt.TransactionReceiptData transactionReceipt) {
        return MintReceipt.builder()
                .minterAddress(transactionReceipt.getFrom())
                .tokenAddress(transactionReceipt.getTo())
                .txHash(transactionReceipt.getTransactionHash())
                .txBlockNumber(transactionReceipt.getBlockNumber())
                .txBlockHash(transactionReceipt.getBlockHash())
                .gasCost(transactionReceipt.getGasUsed())
                .build();
    }

}
