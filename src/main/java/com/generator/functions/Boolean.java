package com.generator.functions;

import java.util.Random;

/**
 * Created by GS-1029 on 01-02-2017.
 */
public class Boolean implements Message{
    private static Random random = new Random();
    public Boolean() {
    }

    public String nextMessage(String data) {
        return String.valueOf(random.nextBoolean());
    }
}
