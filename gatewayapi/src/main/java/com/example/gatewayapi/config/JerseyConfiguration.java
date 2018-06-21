package com.example.gatewayapi.config;

import com.example.gatewayapi.controller.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;


@Component
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        register(HomeController.class);
        register(ErrorController.class);
        register(HealthController.class);
        register(AccountController.class);
        register(AuthenticationRest.class);
    }
}
