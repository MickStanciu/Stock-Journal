package com.example.account.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.xml.ws.Endpoint;


@Component
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        register(Endpoint.class);
    }
}
