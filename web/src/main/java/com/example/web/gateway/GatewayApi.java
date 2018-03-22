package com.example.web.gateway;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ejb.Stateless;
import javax.ws.rs.core.UriBuilder;

@Stateless
public class GatewayApi {

    private static final String SERVICE_URL = "http://gatewayapi:8080";
    private GatewayApiInterface proxy;

    public GatewayApi() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(SERVICE_URL));
        proxy = target.proxy(GatewayApiInterface.class);
    }

    public AuthToken authenticate(String tenantId, String name, String password) {
        return proxy.authenticate(tenantId, name, password);
    }
}

