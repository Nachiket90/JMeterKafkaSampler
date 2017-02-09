package com.generator.functions;

import java.util.Random;

/**
 * Created by GS-1029 on 30-01-2017.
 */

public class AlphaNumeric implements Message{
    private int length=0;
    private String data;
    private static final Random randomStr = new Random();
    private static final char[] subset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public AlphaNumeric(String data) {
        length = Integer.parseInt(data);
    }

    public AlphaNumeric() {
    }

    public String nextMessage(String data) {
        char[] chars = new char[length];
        final int subsetLength = subset.length;
        for (int i = 0; i < length; i++) {
            int index = randomStr.nextInt(subsetLength);
            chars[i] = subset[index];
        }
        return new String(chars);
    }
}
