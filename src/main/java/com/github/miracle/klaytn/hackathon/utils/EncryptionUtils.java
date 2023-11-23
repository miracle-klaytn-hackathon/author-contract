package com.github.miracle.klaytn.hackathon.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtils {

    private EncryptionUtils() {
    }

    public static String generateNonce() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] random = new byte[24];
        secureRandom.nextBytes(random);
        return Base64.getEncoder().encodeToString(random);
    }

}
