package com.example.security.generator;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class KeyGenerator256Bit {
    // Generate a dynamic secret key
    public static String generateDynamicSecretKey() {
        try {
            // Create a KeyGenerator instance with the desired key size (256 bits)
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);

            // Generate the encryption key
            SecretKey secretKey = keyGenerator.generateKey();

            // Get the key in bytes
            byte[] keyBytes = secretKey.getEncoded();

            // Convert the key bytes to a hexadecimal string
            return bytesToHex(keyBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., log it or throw a custom exception)
            throw new RuntimeException("Error generating dynamic secret key", e);
        }
    }

    // Helper function to convert bytes to a hexadecimal string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
