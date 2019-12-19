package com.example.tradelog.api.controller;

import com.example.tradelog.api.core.model.HealthModel;
import com.example.tradelog.api.service.HealthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/health", produces = "application/json")
public class HealthController {

    private HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public HealthModel check() {
        return healthService.getHealthModel();
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public void pong() {

    }

//    @RequestMapping(value = "/test500", method = RequestMethod.GET)
//    public void test500() {
//        throw new ResourceErrorException(ExceptionCode.UNKNOWN);
//    }

}
