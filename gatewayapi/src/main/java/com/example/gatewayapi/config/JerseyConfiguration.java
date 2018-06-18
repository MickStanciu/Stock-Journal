package com.example.gatewayapi.config;

import com.example.gatewayapi.controller.AccountController;
import com.example.gatewayapi.controller.AuthenticationRest;
import com.example.gatewayapi.controller.HealthController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;


@Component
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        register(HealthController.class);
        register(AccountController.class);
        register(AuthenticationRest.class);
    }
}
