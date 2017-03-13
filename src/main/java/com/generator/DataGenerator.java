package com.generator;

import com.generator.functions.FixData;
import com.generator.functions.Message;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by GS-1029 on 07-02-2017.
 */

public class DataGenerator {
    private JSONObject messageProperties;
    StringBuilder stringBuilder = new StringBuilder();
    private Map<String, Message> messageAttributes= new HashMap<String, Message>();
    JSONParser parser = new JSONParser();

    public DataGenerator() {
        Object props = null;
        try {
            props = parser.parse(new FileReader("genericMessages.json"));
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

    private String getClassName(String className) {
        if(className.contains("_")) {
            int index = className.indexOf("_");
            String fhalf = className.substring(0,index);
            String shalf = className.substring(index+1,className.length());
            return fhalf.substring(0,1).toUpperCase() + fhalf.substring(1).toLowerCase()+shalf.substring(0,1).toUpperCase()+shalf.substring(1).toLowerCase();
        }
        else {
            return className.substring(0,1).toUpperCase() + className.substring(1).toLowerCase();
        }
    }

    private void parseJson() throws UnsupportedAttributeTypeException {
        Object messagePropertyValue;
        long seed = 0;
        String className="";

        for (Object reflect_messsageProperty : messageProperties.keySet()) {
            try {
                if (reflect_messsageProperty.toString() == "SEED") {
                    seed = Long.parseLong(messageProperties.get("SEED").toString());
                }
                else {
                    messagePropertyValue = messageProperties.get(reflect_messsageProperty.toString());
                    String paramList = messagePropertyValue.toString();
                    int firstIndex = paramList.indexOf("(") + 1;
                    int lastIndex = paramList.indexOf(")");
                    if (messagePropertyValue.toString().contains("(")) {
                        className = getClassName(messagePropertyValue.toString().substring(0, firstIndex-1));
                        Constructor<?> c = Class.forName("com.generator.functions." + className).getDeclaredConstructor(String.class, long.class);
                        messageAttributes.put(reflect_messsageProperty.toString(), (Message) c.newInstance(new Object[]{paramList.substring(firstIndex, lastIndex), seed}));
                    } else {
                        className = new String("FixData");
                        if (messagePropertyValue instanceof String) {
                            messageAttributes.put(reflect_messsageProperty.toString(),new FixData(messagePropertyValue.toString()));
                        }
                        else if (messagePropertyValue instanceof Long) {
                            messageAttributes.put(reflect_messsageProperty.toString(),new FixData(messagePropertyValue.toString()));
                        }
                        else if (messagePropertyValue instanceof Float) {
                            messageAttributes.put(reflect_messsageProperty.toString(),new FixData(messagePropertyValue.toString()));
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
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
                message.append("\"" + messageAttributeKey.toString() + "\":" + messageAttributes.get(messageAttributeKey).nextMessage() + ",");
                seq++;
            }
            message.setCharAt(message.lastIndexOf(","),' ');
            message.append("}");
        }
        return message.toString();
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            try {
                throw new InsufficientAttributesException("Help : Datagenerator.jar <message schema file in json format> " +
                        "<Kafka producer properties file> <number of messages to send>");
            } catch (InsufficientAttributesException e) {
                e.printStackTrace();
            }
        } else {
            DataGenerator genMessage = new DataGenerator(args[0]);
            InputStream producerInput, messageInput = null;
            String message;
            KafkaProducer<String, String> producer;
            Properties props = new Properties();
            Properties producerProps = new Properties();
            try {
                producerInput = new FileInputStream(args[1]);
                producerProps.load(producerInput);
                BufferedWriter out = new BufferedWriter(new FileWriter("C:\\data.txt"), 32768);
                java.util.Date date = new java.util.Date();
                System.out.println(new Timestamp(date.getTime()));
                System.out.println(new Date(System.currentTimeMillis()));
                Set<Object> keys = producerProps.keySet();
                for (Object key : keys) {
                    props.put(key, producerProps.getProperty((String) key));
                }
                String topic = producerProps.getProperty("topic");
                producer = new KafkaProducer<String, String>(props);

                for (int i = 1; i < Long.parseLong(args[2]); i++) {
                    ProducerRecord<String, String> keyedMsg = new ProducerRecord<String, String>(topic, genMessage.nextMessage());
                    producer.send(keyedMsg);
                }
                System.out.println(new Date(System.currentTimeMillis()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class UnsupportedAttributeTypeException extends Exception {
    public UnsupportedAttributeTypeException(String msg){
        super(msg);
    }
}

class InsufficientAttributesException extends Exception {
    public InsufficientAttributesException(String msg){
        super(msg);
    }
}
