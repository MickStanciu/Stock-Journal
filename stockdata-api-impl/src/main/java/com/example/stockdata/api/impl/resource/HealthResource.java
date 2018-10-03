package com.example.stockdata.api.impl.resource;

import com.example.stockdata.api.spec.model.HealthModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/v1/health", produces = "application/json")
public class HealthResource {

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public HealthModel check() {
        HealthModel model = new HealthModel();
        model.setHello("Yo");
        return model;
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ResponseEntity pong() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
