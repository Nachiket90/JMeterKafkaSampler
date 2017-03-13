package com.generator.functions;

import java.util.SplittableRandom;

/**
 * Created by GS-1029 on 30-01-2017.
 * Data generator for type Integer within a range
 */
public class IntRange implements Message{
    private static SplittableRandom Srandom;
    private int min,max;

    public IntRange(String data, long seed) {
        String[] splitData = data.split(",");
        min = Integer.parseInt(splitData[0]);
        max = Integer.parseInt(splitData[1]);
        Srandom = new SplittableRandom(seed);
    }

    @Override
    public String nextMessage() {
        return String.valueOf(Srandom.nextInt(max-min)+min);
    }
}
