package com.generator.functions;

import java.util.SplittableRandom;

/**
 * Created by GS-1029 on 30-01-2017.
 * Data generator of type number of variable length
 */
public class Numeric implements Message{

    private int length=0;
    private static SplittableRandom Srandom;
    private static final char[] subset = "0123456789".toCharArray();

    public Numeric (String data, long seed) {
        length = Integer.parseInt(data);
        Srandom = new SplittableRandom(seed);
    }

    @Override
    public String nextMessage() {
        char[] chars = new char[length];
        final int subsetLength = subset.length;
        for (int i = 0; i < length; i++) {
            int index = Srandom.nextInt(subsetLength);
            chars[i] = subset[index];
        }
        return new String(chars);
    }
}
