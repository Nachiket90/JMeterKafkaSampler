package com.generator.functions;

/**
 * Created by GS-1029 on 31-01-2017.
 */
public class FixData implements Message{
    private String fixdata;

    public FixData(String data) {
        fixdata = data;
    }

    public FixData() {
    }

    @Override
    public String nextMessage(String data) {
        return fixdata;
    }
}
