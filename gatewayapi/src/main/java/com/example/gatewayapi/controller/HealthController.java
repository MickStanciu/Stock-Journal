package com.example.gatewayapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/api/v1/health", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthController {

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseEntity pong() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @RequestMapping(value = "/check2", method = RequestMethod.POST)
    public ResponseEntity pong2() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
