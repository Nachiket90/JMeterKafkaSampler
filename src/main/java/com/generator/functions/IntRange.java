package com.generator.functions;

import java.util.Random;

/**
 * Created by GS-1029 on 30-01-2017.
 */
public class IntRange implements Message{
    private static final Random randomInt = new Random();
    private int min,max;


    public IntRange() {
    }
    public IntRange(String data) {
        String[] splitData = data.split(",");
        min = Integer.parseInt(splitData[0]);
        max = Integer.parseInt(splitData[1]);
    }

    public static int nextEvent(int min, int max) {
        return randomInt.nextInt((max-min)+1) + min;
    }

    @Override
    public String nextMessage(String data) {
        //String[] splitData = data.split(",");
        //return String.valueOf(nextEvent(Integer.parseInt(splitData[0]), Integer.parseInt(splitData[1])));
        return String.valueOf(nextEvent(min, max));
    }
}
