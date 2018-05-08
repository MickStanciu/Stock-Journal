package com.example.gatewayapi.gateway;

import com.example.common.rest.dto.ErrorDto;
import org.apache.log4j.Logger;

import java.util.List;

abstract class AbstractGateway {

    private static final Logger log = Logger.getLogger(AbstractGateway.class);

    void processErrors(List<ErrorDto> errors) {
        for (ErrorDto error : errors) {
            log.error(error);
        }
    }
}
