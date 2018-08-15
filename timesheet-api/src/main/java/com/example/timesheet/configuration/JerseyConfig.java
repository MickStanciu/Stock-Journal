package com.example.timesheet.configuration;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rest/*")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages(true,"com.example.timesheet");
    }
}
