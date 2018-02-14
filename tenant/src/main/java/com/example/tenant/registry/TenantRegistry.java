package com.example.tenant.registry;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.example.tenant.service.HealthService;
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
    private static final String HOST_PROTOCOL = "http";
    private static final String HOST_NAME = "localhost";
    private static final int HOST_PORT = 8080;
    private static final String CONSUL_ADDRESS = "localhost";

    @Inject
    private HealthService healthService;

    @PostConstruct
    public void register() {
        try {
            NewService.Check serviceCheck = new NewService.Check();
            serviceCheck.setHttp(getEndPoint() + "/health/check");
            serviceCheck.setInterval("30s");
            serviceCheck.setTimeout("600s");

            NewService newService = new NewService();
            newService.setCheck(serviceCheck);
            newService.setId(getId());
            newService.setName(SERVICE_NAME);
            newService.setAddress(HOST_NAME);
            newService.setPort(HOST_PORT);

            ConsulClient client = new ConsulClient(CONSUL_ADDRESS);
            client.agentServiceRegister(newService);
            healthService.passRegistryCheck();
        } catch (Exception ex) {
            healthService.failRegistryCheck();
            log.error("Could not connect to the registry", ex);
        }

        log.info("Service Registered");
    }

    @PreDestroy
    public void unRegister() {
        log.info("Service Un-Registered");
    }

    private String getEndPoint() {
        //todo: detect these dynamically
        String url = HOST_PROTOCOL + "://" + HOST_NAME;

        if (HOST_PORT != 80) {
            url += ":" + HOST_PORT;
        }

        url += "/api";
        return url;
    }

    public String getId() {
        return SERVICE_NAME + ":" + HOST_NAME + ":" + HOST_PORT;
    }
}
