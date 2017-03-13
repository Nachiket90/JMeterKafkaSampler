package com.generator.functions;

/**
 * Created by GS-1029 on 31-01-2017.
 * Data generator for constant user defined value
 */
public class FixData implements Message{
    private Object fixData;

    public FixData(String data) {
           fixData = "\"" + data + "\"";
    }

    public FixData (Long data) {
        fixData = data;
    }

    public FixData (Float data) {
        fixData = data;
    }

    public FixData (String data, long seed) {

    }

    @Override
    public String nextMessage() {
        return fixData.toString();
    }
}
