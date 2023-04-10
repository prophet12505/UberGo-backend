package com.example.ubergo.utils;

import java.util.Random;

public class RandomUsernameGenerator {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int USERNAME_LENGTH = 8;
    private static final Random RANDOM = new Random();

    public static String generate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < USERNAME_LENGTH; i++) {
            int index = RANDOM.nextInt(ALPHABET.length());
            char c = ALPHABET.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }
}
