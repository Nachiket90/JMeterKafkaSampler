package com.generator.functions;

import java.util.Random;

/**
 * Created by GS-1029 on 30-01-2017.
 */
public class RandomData implements Message{
    private static final Random randomIndex = new Random();
    private String[] randomData;

    public RandomData(String data) {
        randomData = data.split(",");
    }

    public RandomData() {
    }

    @Override
    public String nextMessage(String data) {
        //String[] splitData = data.split(",");
        //int index = randomIndex.nextInt(splitData.length-1);
        //return splitData[index];
        int index = randomIndex.nextInt(randomData.length-1);
        return randomData[index];
    }
}
