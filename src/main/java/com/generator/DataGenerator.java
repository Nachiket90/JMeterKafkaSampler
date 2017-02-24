package com.generator;

import com.generator.functions.*;
import com.generator.functions.Boolean;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GS-1029 on 07-02-2017.
 */

//TODO :  distribution in random, serialization support

public class DataGenerator {
    private JSONObject messageProperties;
    StringBuilder stringBuilder = new StringBuilder();
    private Map<String, Message> messageAttributes= new HashMap<String, Message>();
    JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);

    public DataGenerator() {
        Object props = null;
        try {
            props = parser.parse(new FileReader("C:\\genericMessages.json"));
            messageProperties = (JSONObject) props;
            parseJson();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAttributeTypeException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public DataGenerator(String messageSchema) {
        Object props = null;
        try {
            props = parser.parse(new FileReader(messageSchema));
            messageProperties = (JSONObject) props;
            parseJson();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedAttributeTypeException e) {
            e.printStackTrace();
        }
    }

    private void parseJson() throws UnsupportedAttributeTypeException {
        Object messagePropertyValue;
        long seed = 0;

        if (messageProperties.containsKey("SEED")) {
            seed = Long.parseLong(messageProperties.get("SEED").toString());
        }

        for (Object messageProperty : messageProperties.keySet()) {
            messagePropertyValue = messageProperties.get(messageProperty.toString());
            if (messagePropertyValue.toString().contains("TIMESTAMP")) {
                messageAttributes.put(messageProperty.toString(),new TimeStamp());
            }
            else if (messagePropertyValue.toString().contains("STRING")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                messageAttributes.put(messageProperty.toString(),new Alpha(paramList.substring(firstIndex,lastIndex),seed));
            }
            else if (messagePropertyValue.toString().contains("ALPHANUMERIC")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                messageAttributes.put(messageProperty.toString(),new AlphaNumeric(paramList.substring(firstIndex,lastIndex),seed));
            }
            else if (messagePropertyValue.toString().contains("NUMERIC")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                messageAttributes.put(messageProperty.toString(),new Numeric(paramList.substring(firstIndex,lastIndex),seed));
            }
            else if (messagePropertyValue.toString().contains("BOOLEAN")) {
                messageAttributes.put(messageProperty.toString(), new Boolean());
            }
            else if (messagePropertyValue.toString().contains("RANDOM")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                messageAttributes.put(messageProperty.toString(),new RandomData(paramList.substring(firstIndex,lastIndex),seed));
            }
            else if (messagePropertyValue.toString().contains("LONG_RANGE")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                messageAttributes.put(messageProperty.toString(),new LongRange(paramList.substring(firstIndex,lastIndex),seed));
            }
            else if (messagePropertyValue.toString().contains("INT_RANGE")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                messageAttributes.put(messageProperty.toString(),new IntRange(paramList.substring(firstIndex,lastIndex),seed));
            }
            else if (messagePropertyValue.toString().contains("DOUBLE_RANGE")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                messageAttributes.put(messageProperty.toString(),new DoubleRange(paramList.substring(firstIndex,lastIndex),seed));
            }
            else if (messagePropertyValue.toString().contains("IP")) {
                messageAttributes.put(messageProperty.toString(),new Ipv4());
            }
            else if (messagePropertyValue instanceof String) {
                messageAttributes.put(messageProperty.toString(),new FixData(messagePropertyValue.toString()));
            }
            else if (messagePropertyValue instanceof Long) {
                messageAttributes.put(messageProperty.toString(),new FixData(messagePropertyValue.toString()));
            }
            else {
                throw new UnsupportedAttributeTypeException("Unsupported message attribute type");
            }
        }
    }

    public String nextMessage() {
        StringBuilder message = new StringBuilder(450);

        if (messageAttributes.isEmpty()) {

        } else {
            message.append("{");
            int seq = 0;
            for (String messageAttributeKey : messageAttributes.keySet()) {
                //message.append("\"" + messageAttributeKey.toString() + "\":\"" + messageAttributeHashMap.get(messageAttributeKey).nextMessage(funParamList.get(seq)) + "\"," );
                message.append("\"" + messageAttributeKey.toString() + "\":\"" + messageAttributes.get(messageAttributeKey).nextMessage() + "\",");
                seq++;
            }
            message.append("}");
            //System.out.println(message.capacity());
        }
        return message.toString();
    }

    public static void main(String[] args) {
        DataGenerator genMessage = new DataGenerator("C:\\genericMessages.json");
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("C:\\data.txt"), 32768);
            java.util.Date date= new java.util.Date();
            //HashMap<String, Message> messageAttributes = genMessage.parseJson();
            System.out.println(new Timestamp(date.getTime()));
            System.out.println(new Date(System.currentTimeMillis()));

            //it.unimi.dsi.util.XorShift128PlusRandom XrandomInt = new XorShift128PlusRandom();
            //Random randomInt = new Random();

            for (int i=1; i<1000000; i++) {
                //genMessage.nextMessage(messageAttributes);
                genMessage.nextMessage();
               //System.out.println(genMessage.nextMessage(messageAttributes));

                //XrandomInt.nextInt();
                //randomInt.nextInt();

                //XrandomInt.nextLong(1000000000);
                //randomInt.nextLong();
                //ThreadLocalRandom.current().nextLong(1000000,10000000);

                //XrandomInt.nextDouble();
                //XrandomInt.doubles(1.2, 22.0);
                //randomInt.nextDouble();
                //ThreadLocalRandom.current().nextDouble(1.2, 22.00);

            }
            //System.out.println(new Timestamp(date.getTime()));
            System.out.println(new Date(System.currentTimeMillis()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class UnsupportedAttributeTypeException extends Exception {
    public UnsupportedAttributeTypeException(String msg){
        super(msg);
    }
}