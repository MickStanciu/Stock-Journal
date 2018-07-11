package com.example.gatewayapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/api/v1/error", produces = MediaType.APPLICATION_JSON_VALUE)
public class ErrorController {

    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping(value = "/401", method = RequestMethod.GET)
    public ResponseEntity e401() {
        log.error("Not authorized");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
