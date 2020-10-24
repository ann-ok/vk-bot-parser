package org.vkbot.handler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyHandler {
    private static final String baseFileName = "vk.properties";

    Properties properties = new Properties();

    public PropertyHandler() throws IOException {
        this(baseFileName);
    }

    public PropertyHandler(String fileName) throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        properties.load(new FileInputStream(rootPath + fileName));
    }

    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
