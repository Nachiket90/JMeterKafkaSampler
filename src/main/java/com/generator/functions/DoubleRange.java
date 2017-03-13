package com.generator.functions;

import java.util.SplittableRandom;

/**
 * Created by GS-1029 on 30-01-2017.
 * Data generator of type double within a range
 */
public class DoubleRange implements Message{
    private double min,max;
    private static SplittableRandom Srandom;

    public DoubleRange(String data, long seed) {
        String[] splitData = data.split(",");
        min = Double.parseDouble(splitData[0]);
        max = Double.parseDouble(splitData[1]);
        Srandom = new SplittableRandom(seed);

    }

    @Override
    public String nextMessage() {
        return String.valueOf(Srandom.nextDouble()+min);
    }
}
