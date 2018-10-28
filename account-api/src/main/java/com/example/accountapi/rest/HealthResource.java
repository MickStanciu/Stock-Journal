package com.example.accountapi.rest;

import com.example.accountapi.exception.ExceptionCode;
import com.example.accountapi.exception.ResourceErrorException;
import com.example.accountapi.model.HealthModel;
import com.example.accountapi.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/health", produces = "application/json")
public class HealthResource {

    @Autowired
    private HealthService service;

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public HealthModel check() {
        return service.getHealth();
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public void pong() {

    }

    @RequestMapping(value = "/test500", method = RequestMethod.GET)
    public void test500() {
        throw new ResourceErrorException(ExceptionCode.UNKNOWN);
    }
}
