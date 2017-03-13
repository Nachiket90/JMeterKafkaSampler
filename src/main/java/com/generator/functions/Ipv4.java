package com.generator.functions;

import java.util.SplittableRandom;

/**
 * Created by GS-1029 on 08-02-2017.
 * Data generator for IPV4 format IP record
 */
public class Ipv4 implements Message{
    private static SplittableRandom Srandom;

    public Ipv4(String data, long seed) {
        Srandom = new SplittableRandom(seed);
    }

    @Override
    public String nextMessage() {
        return Srandom.nextInt(255)+"."+Srandom.nextInt(255)+"."+Srandom.nextInt(255)+"."+Srandom.nextInt(255);
    }
}
