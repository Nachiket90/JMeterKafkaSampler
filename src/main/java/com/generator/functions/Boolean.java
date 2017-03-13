package com.generator.functions;

import java.util.SplittableRandom;

/**
 * Created by GS-1029 on 01-02-2017.
 * Data generator of type Boolean
 */
public class Boolean implements Message {

    private static SplittableRandom Srandom;

    public Boolean(String data, long seed) {
        Srandom = new SplittableRandom(seed);
    }


    @Override
    public String nextMessage() {
        return String.valueOf(Srandom.nextBoolean());
    }
}
