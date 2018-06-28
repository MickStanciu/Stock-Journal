package com.example.gatewayapi.gateway;

import com.example.common.rest.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;


abstract class AbstractGateway {

    private static final Logger log = LoggerFactory.getLogger(AbstractGateway.class);

    private RestTemplate restTemplate;

    public AbstractGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    void processErrors(List<ErrorDto> errors) {
        for (ErrorDto error : errors) {
            log.error("", error);
        }
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
