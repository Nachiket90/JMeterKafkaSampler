package com.generator.functions;

import java.util.Random;

/**
 * Created by GS-1029 on 30-01-2017.
 */
public class Numeric implements Message{

    private int length=0;
    private static final Random randomStr = new Random();
    private static final char[] subset = "0123456789".toCharArray();

    public Numeric () {
    }

    public Numeric (String data) {
        length = Integer.parseInt(data);
    }

    @Override
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
