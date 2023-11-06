package com.example.security.generator;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class KeyGenerator256Bit {
    public static void main(String[] args) {
        try {
            // Create a KeyGenerator instance with the desired key size (256 bits)
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256); // You can change the algorithm if needed

            // Generate the encryption key
            SecretKey secretKey = keyGenerator.generateKey();

            // Get the key in bytes
            byte[] keyBytes = secretKey.getEncoded();

            // Print the key as a hexadecimal string
            System.out.println(bytesToHex(keyBytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Helper function to convert bytes to a hexadecimal string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
