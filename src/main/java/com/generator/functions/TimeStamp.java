package com.generator.functions;

import java.util.concurrent.TimeUnit;

/**
 * Created by GS-1029 on 31-01-2017.
 */
public class TimeStamp implements Message{
    public TimeStamp() {
    }

    @Override
    public String nextMessage(String data) {
        return String.valueOf(TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()));
    }
}
