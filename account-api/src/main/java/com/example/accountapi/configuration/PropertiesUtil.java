package com.example.accountapi.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

    public Properties getProperties() {
        Properties properties = new Properties();

        final InputStream stream = PropertyProducer.class.getResourceAsStream("/application.properties");
        if (stream != null) {
            try {
                properties.load(stream);
            } catch (final IOException e) {
                throw new RuntimeException("Configuration could not be loaded!");
            }
        }

        return properties;
    }
}
