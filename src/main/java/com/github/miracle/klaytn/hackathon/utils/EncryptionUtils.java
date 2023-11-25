package com.github.miracle.klaytn.hackathon.utils;

import java.security.SecureRandom;

import org.apache.commons.lang3.RandomStringUtils;

public class EncryptionUtils {

    private EncryptionUtils() {
    }

    public static String generateNonce(int length) {
        SecureRandom random = new SecureRandom();
        return RandomStringUtils.random(length, 0, 0, true, true, null, random);
    }

}
