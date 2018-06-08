package com.example.account.controller;

import com.example.account.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/health", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthController {

    private HealthService service;

    @Autowired
    public HealthController(HealthService service) {
        this.service = service;
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ResponseEntity pong() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseEntity<?> checkFirstRecord() {
        HttpStatus status = HttpStatus.OK;

        if (!service.isOk()) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        }

        return ResponseEntity.status(status).body(service.getHealth());
    }

}
