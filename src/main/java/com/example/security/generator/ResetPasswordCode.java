package com.example.security.generator;

import java.util.Random;

public class ResetPasswordCode {
    public static int generate() {
        Random random = new Random();
        return (100000 + random.nextInt(900000));
    }
}
