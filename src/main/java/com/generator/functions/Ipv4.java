package com.generator.functions;

import java.util.Random;

/**
 * Created by GS-1029 on 08-02-2017.
 */
public class Ipv4 implements Message{
    private static final Random randomInt = new Random();

    public Ipv4() {
    }

    @Override
    public String nextMessage(String data) {
        return randomInt.nextInt(255)+"."+randomInt.nextInt(255)+"."+randomInt.nextInt(255)+"."+randomInt.nextInt(255);
    }
}
