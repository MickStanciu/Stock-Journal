package com.example.gatewayapi.gateway.tenantApi;

import com.example.common.rest.dto.ErrorDto;
import com.example.gatewayapi.gateway.ResponseEnvelope;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ejb.Stateless;
import javax.swing.text.html.Option;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Optional;

@Stateless
public class TenantGateway {

    private static final Logger log = Logger.getLogger(TenantGateway.class);
    private static final String SERVICE_URL = "http://tenantapi:8080/api";
    private TenantInterface proxy;

    public TenantGateway() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(SERVICE_URL));
        proxy = target.proxy(TenantInterface.class);
    }

    public Optional<Tenant> getTenant(String tenantId) {
        Response response = proxy.tenantByUUID(tenantId);
        ResponseEnvelope<Tenant> envelope = response.readEntity(new GenericType<ResponseEnvelope<Tenant>>(){});
        response.close();

        if (response.getStatus() != 200 && envelope.getErrors() != null) {
            processErrors(envelope.getErrors());
        }

        if (response.getStatus() == 200 && envelope.getData() != null) {
            return Optional.of(envelope.getData());
        }
        return Optional.empty();
    }

    private void processErrors(List<ErrorDto> errors) {
        for (ErrorDto error : errors) {
            log.error(error);
        }
    }
}
