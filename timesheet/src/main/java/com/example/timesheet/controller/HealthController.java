package com.example.timesheet.controller;

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

    @GET
    @Path("/ping")
    public ResponseEntity pong() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
