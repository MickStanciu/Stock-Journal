package com.example.gatewayapi.gateway;

import com.example.common.rest.dto.ErrorDto;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

abstract class AbstractGateway {

    private static final Logger log = LoggerFactory.getLogger(AbstractGateway.class);

    void processErrors(List<ErrorDto> errors) {
        for (ErrorDto error : errors) {
            log.error("", error);
        }
    }

    public ResteasyWebTarget getTarget(URI uri) {
        ResteasyClient client = new ResteasyClientBuilder()
                .build();
        return client.target(uri);
    }

}
