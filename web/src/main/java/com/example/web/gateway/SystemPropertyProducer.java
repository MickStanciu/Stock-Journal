package com.example.web.gateway;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.Map;

public class SystemPropertyProducer {
    private Map<String, String> environmentVariables;

    @PostConstruct
    public void init() {
        environmentVariables = System.getenv();
    }

    @SystemProperty
    @Produces
    public String findStringProperty(final InjectionPoint ip) {
        SystemProperty annotation = ip.getAnnotated().getAnnotation(SystemProperty.class);
        String key = annotation.value();
        if (environmentVariables.containsKey(key)) {
            return environmentVariables.get(key);
        }
        throw new IllegalStateException("System property '" + key + "' is not defined");
    }

    @SystemProperty
    @Produces
    public boolean findBooleanProperty(final InjectionPoint ip) {
        SystemProperty annotation = ip.getAnnotated().getAnnotation(SystemProperty.class);
        String key = annotation.value();
        if (environmentVariables.containsKey(key)) {
            return Boolean.valueOf(environmentVariables.get(key));
        }
        throw new IllegalStateException("System property '" + key + "' is not defined");
    }

}
