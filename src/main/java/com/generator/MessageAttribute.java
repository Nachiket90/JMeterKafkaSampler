package com.generator;

import org.apache.jmeter.testelement.AbstractTestElement;

/**
 * Created by GS-1029 on 27-01-2017.
 */
public class MessageAttribute extends AbstractTestElement{
    public static final String PROPERTY_NAME = "propertyName";
    public static final String PROPERTY_VALUE = "propertyValue";

    public String getPropertyName() {
        return getProperty(PROPERTY_NAME).toString();
    }

    public void setPropertyName(String propertyName) {
        setProperty(PROPERTY_NAME,propertyName);
    }

    public String getPropertyValue() {
        return getProperty(PROPERTY_VALUE).toString();
    }

    public void setPropertyValue(String propertyValue) {
        setProperty(PROPERTY_VALUE, propertyValue);
    }

    public MessageAttribute(String name, String value) {
        setPropertyName(name);
        setPropertyValue(value);
    }
}
