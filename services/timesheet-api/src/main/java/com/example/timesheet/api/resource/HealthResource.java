package com.example.timesheet.api.resource;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value = "/api/v1/health", produces = "application/json")
public class HealthResource {

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public void ping() {

    }
}