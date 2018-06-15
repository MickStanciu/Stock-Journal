package com.example.tenant.config;

import com.example.tenant.controller.HealthController;
import com.example.tenant.controller.TenantController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;


@Component
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        register(HealthController.class);
        register(TenantController.class);
    }
}
