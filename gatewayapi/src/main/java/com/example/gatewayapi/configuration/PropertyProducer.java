package com.example.gatewayapi.configuration;

import com.example.gatewayapi.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.Properties;

public class PropertyProducer {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    private Properties properties;

    @PostConstruct
    public void init() {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        properties = propertiesUtil.getProperties();
        log.debug(PropertyProducer.class.getName() + " was initialized");
    }

    @Property
    @Produces
    public String producesString(final InjectionPoint ip) {
        return this.properties.getProperty(getKey(ip));
    }

    @Property
    @Produces
    public int producesInteger(final InjectionPoint ip) {
        return Integer.valueOf(this.properties.getProperty(getKey(ip)));
    }

    @Property
    @Produces
    public boolean producesBoolean(final InjectionPoint ip) {
        return Boolean.valueOf(this.properties.getProperty(getKey(ip)));
    }

    private String getKey(final InjectionPoint ip) {
        if (!ip.getAnnotated().isAnnotationPresent(Property.class)) {
            return "";
        }

        return ip.getAnnotated().getAnnotation(Property.class).value();
    }

}
