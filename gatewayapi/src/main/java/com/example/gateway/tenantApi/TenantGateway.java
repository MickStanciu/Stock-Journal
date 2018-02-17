package com.example.gateway.tenantApi;

import com.example.gateway.ResponseEnvelope;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ejb.Stateless;
import javax.ws.rs.core.UriBuilder;

@Stateless
public class TenantGateway {

    private static final String SERVICE_URL = "http://tenantapi:8080";
    private TenantInterface proxy;

    public TenantGateway() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(SERVICE_URL));
        proxy = target.proxy(TenantInterface.class);
    }

    public Tenant getTenant(String tenantId) {
        ResponseEnvelope<Tenant> tenantResponseEnvelope = proxy.tenantByUUID(tenantId);
        return tenantResponseEnvelope.getData();
    }

}
