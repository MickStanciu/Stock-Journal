package com.example.gatewayapi.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Produces("application/json")
@Path("/")
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GET
    public String test() {
        log.info("PL");
        return "index";
    }
}
