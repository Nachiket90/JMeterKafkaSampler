package com.generator.functions;

import java.util.SplittableRandom;

/**
 * Created by GS-1029 on 30-01-2017.
 * Data generator of type Long within a range
 */
public class LongRange implements Message{
    private Long min,max;
    private static SplittableRandom Srandom;

    public LongRange(String data, long seed) {
        String[] splitData = data.split(",");
        min = Long.parseLong(splitData[0]);
        max = Long.parseLong(splitData[1]);
        Srandom = new SplittableRandom(seed);
    }

    @Override
    public String nextMessage() {
        return String.valueOf(Srandom.nextLong(max-min))+min;
    }
}
