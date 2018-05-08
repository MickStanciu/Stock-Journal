package com.example.gatewayapi.gateway;

import com.example.tenant.model.TenantModel;
import com.example.tenant.rest.TenantRestInterface;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

@Stateless
public class TenantGateway extends AbstractGateway {

    @Inject
    @SystemProperty("TENANT_API_ADDRESS")
    private String SERVICE_URL;

    private TenantRestInterface proxy;

    @PostConstruct
    public void init() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(SERVICE_URL + "/api"));
        proxy = target.proxy(TenantRestInterface.class);
    }

    public Optional<TenantModel> getTenant(String tenantId) {
        Response response = proxy.tenantByUUID(tenantId);
        ResponseEnvelope<TenantModel> envelope = response.readEntity(new GenericType<ResponseEnvelope<TenantModel>>(){});
        response.close();

        if (response.getStatus() != 200 && envelope.getErrors() != null) {
            processErrors(envelope.getErrors());
        }

        if (response.getStatus() == 200 && envelope.getData() != null) {
            return Optional.of(envelope.getData());
        }
        return Optional.empty();
    }

}
