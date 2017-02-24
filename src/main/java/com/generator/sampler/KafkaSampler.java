package com.generator.sampler;

import com.generator.DataGenerator;
import com.generator.util.ProducerPropsKeys;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;

/**
 * Created by GS-1029 on 07/02/17.
 */
public class KafkaSampler extends AbstractJavaSamplerClient {


    private KafkaProducer<String,String> producer;
    DataGenerator dataGen;

    private String topic;


    @Override
    public Arguments getDefaultParameters() {

        Arguments defaultParameters = new Arguments();

        defaultParameters.addArgument(ProducerPropsKeys.KAFKA_PRODUCER_CONFIG, "");
        defaultParameters.addArgument(ProducerPropsKeys.KAFKA_MESSAGE_SCHEMA, "");
        return defaultParameters;
    }

    @Override
    public void setupTest(JavaSamplerContext context) {

        InputStream producerInput,messageInput = null;
        String message;
        Properties props = new Properties();

        try {
            producerInput = new FileInputStream(context.getParameter(ProducerPropsKeys.KAFKA_PRODUCER_CONFIG));
            dataGen = new DataGenerator(context.getParameter(ProducerPropsKeys.KAFKA_MESSAGE_SCHEMA));
            Properties producerProps = new Properties();
            producerProps.load(producerInput);

            Set<Object> keys = producerProps.keySet();

            for (Object key: keys) {
                props.put(key,producerProps.getProperty((String) key));
            }

            topic = producerProps.getProperty("topic");

            //TODO add message json parsing and properties validation and error handling
            Thread.currentThread().setContextClassLoader(null);
            producer = new KafkaProducer<String, String>(props);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult result = newSampleResult();
        String message = context.getParameter(ProducerPropsKeys.KAFKA_MESSAGE_SCHEMA);
        sampleResultStart(result, message);

        try {

            ProducerRecord<String, String> keyedMsg =  new ProducerRecord<String, String>(topic, dataGen.nextMessage());
            producer.send(keyedMsg);
            sampleResultSuccess(result, null);
        } catch (Exception e) {
            sampleResultFailed(result, "500", e);
        }

        return result;

    }

    @Override
    public void teardownTest(JavaSamplerContext context) {

        producer.close();

    }


    private SampleResult newSampleResult() {
        SampleResult result = new SampleResult();
        result.setDataEncoding(StandardCharsets.UTF_8.name());
        result.setDataType(SampleResult.TEXT);
        return result;
    }

    private void sampleResultStart(SampleResult result, String data) {
        result.setSamplerData(data);
        result.sampleStart();
    }

    private void sampleResultSuccess(SampleResult result, /* @Nullable */ String response) {
        result.sampleEnd();
        result.setSuccessful(true);
        result.setResponseCodeOK();
        if (response != null) {
            result.setResponseData(response, StandardCharsets.UTF_8.name());
        }
        else {
            result.setResponseData("No response required", StandardCharsets.UTF_8.name());
        }
    }

    private void sampleResultFailed(SampleResult result, String reason) {
        result.sampleEnd();
        result.setSuccessful(false);
        result.setResponseCode(reason);
    }

    private void sampleResultFailed(SampleResult result, String reason, Exception exception) {
        sampleResultFailed(result, reason);
        result.setResponseMessage("Exception: " + exception);
        result.setResponseData(getStackTrace(exception), StandardCharsets.UTF_8.name());
    }

    private String getStackTrace(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }


}
