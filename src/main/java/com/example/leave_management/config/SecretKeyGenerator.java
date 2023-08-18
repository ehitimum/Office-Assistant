package com.example.leave_management.config;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {

    public static void main(String[] args) {
        // Generate a random 256-bit (32-byte) secret key
        byte[] keyBytes = generateRandomKey(32);

        // Encode the key in base64
        String encodedKey = Base64.getEncoder().encodeToString(keyBytes);

        System.out.println("Generated Secret Key: " + encodedKey);
    }

    private static byte[] generateRandomKey(int keyLength) {
        byte[] keyBytes = new byte[keyLength];
        new SecureRandom().nextBytes(keyBytes);
        return keyBytes;
    }
}
