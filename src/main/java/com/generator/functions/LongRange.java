package com.generator.functions;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by GS-1029 on 30-01-2017.
 */
public class LongRange implements Message{
    private Long min,max;

    public LongRange() {
    }

    public LongRange(String data) {
        String[] splitData = data.split(",");
        min = Long.parseLong(splitData[0]);
        max = Long.parseLong(splitData[1]);
    }

    public static long nextEvent(long min, long max) {
        //return  ThreadLocalRandom.current() .nextLong(min,max);
        return ThreadLocalRandom.current().nextLong(min,max);
    }

    @Override
    public String nextMessage(String data) {
        //String[] splitData = data.split(",");
        //return String.valueOf(nextEvent(Long.parseLong(splitData[0]), Long.parseLong(splitData[1])));
        return String.valueOf(ThreadLocalRandom.current().nextLong(min,max));
    }
}
