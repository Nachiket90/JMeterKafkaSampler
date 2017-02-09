package com.generator.functions;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by GS-1029 on 30-01-2017.
 */
public class DoubleRange implements Message{
    private double min,max;

    public DoubleRange(String data) {
        String[] splitData = data.split(",");
        min = Double.parseDouble(splitData[0]);
        max = Double.parseDouble(splitData[1]);
    }

    public DoubleRange() {
    }

    public static double nextEvent(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min,max);
    }

    @Override
    public String nextMessage(String data) {
        //String[] splitData = data.split(",");
        //return String.valueOf(ThreadLocalRandom.current().nextDouble(Double.parseDouble(splitData[0]), Double.parseDouble(splitData[1])));
        return String.valueOf(ThreadLocalRandom.current().nextDouble(min,max));
    }
}
