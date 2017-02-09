package com.generator;

import com.generator.functions.*;
import com.generator.functions.Boolean;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * Created by GS-1029 on 07-02-2017.
 */

//TODO : seed in random, distribution in random, add IP attribute, serialization support

public class DataGenerator {
    private JSONObject messageProperties;
    StringBuilder stringBuilder = new StringBuilder();
    private Map<String, String> json= new HashMap<>();
    JSONParser parser = new JSONParser();
    private ArrayList<String> funParamList = new ArrayList<String>();

    public DataGenerator() {
        Object props = null;
        try {
            props = parser.parse(new FileReader("C:\\genericMessages.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        messageProperties = (JSONObject) props;
    }

    public DataGenerator(List<MessageAttribute> messageAttributes) {
        //this.messageAttributes = messageAttributes;
        Object props = null;
        try {
            props = parser.parse(new FileReader("genericMessages.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        messageProperties = (JSONObject) props;

        if (messageAttributes != null && !messageAttributes.isEmpty()){

            for (MessageAttribute messageAttribute : messageAttributes){

                if (isNotEmpty(messageAttribute.getPropertyName()) && isNotEmpty(messageAttribute.getPropertyValue())) {
                    stringBuilder.append(",\""+messageAttribute.getPropertyName()+"\":\""+messageAttribute.getPropertyValue()+"\"");
                }
            }
        }
    }

    private HashMap<String, Message> parseJson() throws UnsupportedAttributeTypeException {
        HashMap<String,Message> methodCallTree = new HashMap<String,Message>();
        Object messagePropertyValue;
        for (Object messageProperty : messageProperties.keySet()) {
            messagePropertyValue = messageProperties.get(messageProperty.toString());
            if (messagePropertyValue.toString().contains("TIMESTAMP")) {
                methodCallTree.put(messageProperty.toString(),new TimeStamp());
                funParamList.add("");
            }
            else if (messagePropertyValue.toString().contains("STRING")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                methodCallTree.put(messageProperty.toString(),new Alpha(paramList.substring(firstIndex,lastIndex)));
                funParamList.add(paramList.substring(firstIndex,lastIndex));
            }
            else if (messagePropertyValue.toString().contains("ALPHANUMERIC")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                methodCallTree.put(messageProperty.toString(),new AlphaNumeric(paramList.substring(firstIndex,lastIndex)));
                funParamList.add(paramList.substring(firstIndex,lastIndex));
            }
            else if (messagePropertyValue.toString().contains("NUMERIC")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                methodCallTree.put(messageProperty.toString(),new Numeric(paramList.substring(firstIndex,lastIndex)));
                funParamList.add(paramList.substring(firstIndex,lastIndex));
            }
            else if (messagePropertyValue.toString().contains("BOOLEAN")) {
                methodCallTree.put(messageProperty.toString(), new Boolean());
                funParamList.add("");
            }
            else if (messagePropertyValue.toString().contains("RANDOM")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                methodCallTree.put(messageProperty.toString(),new RandomData(paramList.substring(firstIndex,lastIndex)));
                funParamList.add(paramList.substring(firstIndex,lastIndex));
            }
            else if (messagePropertyValue.toString().contains("LONG_RANGE")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                methodCallTree.put(messageProperty.toString(),new LongRange(paramList.substring(firstIndex,lastIndex)));
                funParamList.add(paramList.substring(firstIndex,lastIndex));
            }
            else if (messagePropertyValue.toString().contains("INT_RANGE")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                methodCallTree.put(messageProperty.toString(),new IntRange(paramList.substring(firstIndex,lastIndex)));
                funParamList.add(paramList.substring(firstIndex,lastIndex));
            }
            else if (messagePropertyValue.toString().contains("DOUBLE_RANGE")) {
                String paramList = messagePropertyValue.toString();
                int firstIndex = paramList.indexOf("(") + 1;
                int lastIndex = paramList.indexOf(")");
                methodCallTree.put(messageProperty.toString(),new DoubleRange(paramList.substring(firstIndex,lastIndex)));
                funParamList.add(paramList.substring(firstIndex,lastIndex));
            }
            else if (messagePropertyValue.toString().contains("IP")) {
                methodCallTree.put(messageProperty.toString(),new Ipv4());
                funParamList.add("");
            }
            else if (messagePropertyValue instanceof String) {
                methodCallTree.put(messageProperty.toString(),new FixData(messagePropertyValue.toString()));
                funParamList.add(messagePropertyValue.toString());
            }
            else if (messagePropertyValue instanceof Long) {
                methodCallTree.put(messageProperty.toString(),new FixData(messagePropertyValue.toString()));
                funParamList.add(messagePropertyValue.toString());
            }
            else {
                throw new UnsupportedAttributeTypeException("Unsupported message attribute type");
            }
        }
        return methodCallTree;
    }

    public String nextMessage(HashMap<String, Message> messageAttributeHashMap) {
        StringBuilder message = new StringBuilder();

        if (messageAttributeHashMap.isEmpty()) {

        } else {
            message.append("{");
            int seq = 0;
            for (String messageAttributeKey : messageAttributeHashMap.keySet()) {
                //message.append("\"" + messageAttributeKey.toString() + "\":\"" + messageAttributeHashMap.get(messageAttributeKey).nextMessage(funParamList.get(seq)) + "\"," );
                message.append("\"" + messageAttributeKey.toString() + "\":\"" + messageAttributeHashMap.get(messageAttributeKey).nextMessage("") + "\",");
                seq++;
            }
            message.append("}");
        }
        return message.toString();
    }

    public static void main(String[] args) {
        DataGenerator genMessage = new DataGenerator();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("C:\\data.txt"), 32768);
            java.util.Date date= new java.util.Date();
            HashMap<String, Message> messageAttributes = genMessage.parseJson();
            System.out.println(new Timestamp(date.getTime()));
            System.out.println(new Date(System.currentTimeMillis()));

            for (int i=1; i<10000000; i++) {
                genMessage.nextMessage(messageAttributes);
                //genMessage.nextTMessage();
               //System.out.println(genMessage.nextMessage(messageAttributes));
            }
            //System.out.println(new Timestamp(date.getTime()));
            System.out.println(new Date(System.currentTimeMillis()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAttributeTypeException e) {
            e.printStackTrace();
        }
    }
}


class UnsupportedAttributeTypeException extends Exception {
    public UnsupportedAttributeTypeException(String msg){
        super(msg);
    }
}