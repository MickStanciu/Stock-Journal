package com.example.stockdata.api.impl.configuration;

import com.example.stockdata.api.impl.rest.HealthResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        register(HealthResource.class);
    }
}
