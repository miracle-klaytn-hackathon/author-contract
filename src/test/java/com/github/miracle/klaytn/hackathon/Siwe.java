package com.github.miracle.klaytn.hackathon;

import java.security.SecureRandom;
import java.util.Base64;

import com.moonstoneid.siwe.SiweMessage;
import com.moonstoneid.siwe.error.SiweException;

public class Siwe {
    public static void main(String[] args) {
        
        System.out.println(generateNonce());

        String message = "example.com wants you to sign in with your Ethereum account:\n" +
                "0xAd472fbB6781BbBDfC4Efea378ed428083541748\n\n" +
                "Sign in to use the app.\n\n" +
                "URI: https://example.com\n" +
                "Version: 1\n" +
                "Chain ID: 1\n" +
                "Nonce: rF6y+7FTO0boMh6Teq2oooT6TFTuN9DI\n" +
                "Issued At: 2022-06-17T22:29:40.065529400+02:00";
        String signature = "0x2ce1f57908b3d1cfece352a90cec9beab0452829a0bf741d26016d60676d63" +
                "807b5080b4cc387edbe741203387ef0b8a6e79743f636512cc48c80cbb12ffa8261b";
        try {
            // Parse string to SiweMessage
            SiweMessage siwe = new SiweMessage.Parser().parse(message);
            // Verify integrity of SiweMessage by matching its signature
            siwe.verify("example.com", siwe.getNonce(), signature);
        } catch (SiweException e) {
            // Handle exception
        }
    }

    private static String generateNonce() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] random = new byte[24];
        secureRandom.nextBytes(random);
        return Base64.getEncoder().encodeToString(random);
    }
}
