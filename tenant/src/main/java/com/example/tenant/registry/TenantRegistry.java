package com.example.tenant.registry;

import com.example.tenant.service.HealthService;
import com.example.tenant.service.RegistryService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class TenantRegistry {

    private static final Logger log = Logger.getLogger(TenantRegistry.class);
    private static final String SERVICE_NAME = "tenant";


    @Inject
    private HealthService healthService;

    @Inject
    private RegistryService registryService;

    @PostConstruct
    public void register() {
        if (registryService.register(SERVICE_NAME, getHealthUri())) {
            healthService.passRegistryCheck();
        } else {
            healthService.failRegistryCheck();
        }
    }

    @PreDestroy
    public void unRegister() {
        registryService.unRegister();
    }

    private String getHealthUri() {
        return "/health/check";
    }
}
