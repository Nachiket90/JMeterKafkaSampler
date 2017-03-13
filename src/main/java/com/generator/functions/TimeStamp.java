package com.generator.functions;

import java.util.concurrent.TimeUnit;

/**
 * Created by GS-1029 on 31-01-2017.
 * Data generator of type long.
 */
public class Timestamp implements Message{
    public Timestamp(String data, long seed) {
    }

    @Override
    public String nextMessage() {
        return String.valueOf(TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()));
    }
}
