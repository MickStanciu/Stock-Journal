package com.example.account.controller;

import com.example.account.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Component
@Path("/api/v1/health")
@Produces("application/json")
public class HealthController {

    private HealthService service;

    @Autowired
    public HealthController(HealthService service) {
        this.service = service;
    }

    @GET
    @Path("/ping")
    public ResponseEntity pong() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GET
    @Path("/check")
    public ResponseEntity<?> checkFirstRecord() {
        HttpStatus status = HttpStatus.OK;

        if (!service.isOk()) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        }

        return ResponseEntity.status(status).body(service.getHealth());
    }
}
