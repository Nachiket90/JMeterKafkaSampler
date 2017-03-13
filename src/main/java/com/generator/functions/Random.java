package com.generator.functions;

import java.util.SplittableRandom;

/**
 * Created by GS-1029 on 30-01-2017.
 * Data generator from a user defined list of data type.
 */
public class Random implements Message{
    private static SplittableRandom Srandom;
    private String[] randomData;
    private String datatype="";

    public Random(String data, long seed) {
        randomData = data.split(",");
        if (isDouble(randomData[0]) || isLong(randomData[0])) {
            datatype = "number";
        } else {
            datatype = "str";
        }
        Srandom = new SplittableRandom(seed);
    }

    @Override
    public String nextMessage() {
        int index = Srandom.nextInt(randomData.length-1);
        if (datatype == "number")
        return randomData[index];
        return "\""+randomData[index]+"\"";

    }

    // Data type is Double
    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Data type is Long
    boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
