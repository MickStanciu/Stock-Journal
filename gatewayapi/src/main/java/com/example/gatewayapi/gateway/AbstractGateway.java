package com.example.gatewayapi.gateway;

import com.example.common.rest.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

abstract class AbstractGateway {

    private static final Logger log = LoggerFactory.getLogger(AbstractGateway.class);

    void processErrors(List<ErrorDto> errors) {
        for (ErrorDto error : errors) {
            log.error("", error);
        }
    }
}
