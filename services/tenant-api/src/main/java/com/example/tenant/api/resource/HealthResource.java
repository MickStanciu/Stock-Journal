package com.example.tenant.api.resource;

import com.example.tenant.api.service.HealthService;
import com.example.tenant.api.spec.model.HealthModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/health", produces = "application/json")
public class HealthResource {

    private HealthService service;

    public HealthResource(HealthService service) {
        this.service = service;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public HealthModel check() {
        return service.getHealth();
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public void pong() {

    }
}
