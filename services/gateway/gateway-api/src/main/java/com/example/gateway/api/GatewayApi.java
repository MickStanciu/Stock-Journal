package com.example.gateway.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApi {

    private static final Logger log = LoggerFactory.getLogger(GatewayApi.class);

    public static void main(String[] args) {
        SpringApplication.run(GatewayApi.class, args);
    }

}
