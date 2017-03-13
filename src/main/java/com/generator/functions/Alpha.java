package com.generator.functions;

import java.util.SplittableRandom;

/**
 * Created by GS-1029 on 30-01-2017.
 * Data generation of char type of variable length
 */
public class Alpha implements Message{;
    private int length;
    private static SplittableRandom Srandom;
    private static final char[] subset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public Alpha(String data, long seed) {
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
        return "\""+new String(chars)+"\"";
    }
}
