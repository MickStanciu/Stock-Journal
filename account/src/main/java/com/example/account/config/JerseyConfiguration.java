package com.example.account.config;

import com.example.account.controller.AccountController;
import com.example.account.controller.HealthController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;



@Component
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        register(HealthController.class);
        register(AccountController.class);
    }
}
