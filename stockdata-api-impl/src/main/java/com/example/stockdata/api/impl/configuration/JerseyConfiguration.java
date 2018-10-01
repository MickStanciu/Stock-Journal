package com.example.stockdata.api.impl.configuration;

import com.example.stockdata.api.impl.rest.HealthResource;
import org.glassfish.jersey.server.ResourceConfig;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {

    }

    @PostConstruct
    public void setUp() {
        register(HealthResource.class);
    }
}
