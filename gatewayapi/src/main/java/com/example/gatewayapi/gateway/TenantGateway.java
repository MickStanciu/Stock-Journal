package com.example.gatewayapi.gateway;

import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.tenant.model.TenantModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;

@Component
public class TenantGateway extends AbstractGateway {

    @Value("${gateway.tenant.address}")
    private String SERVICE_URL;

    private WebTarget target;

    @PostConstruct
    public void init() {
        Client client = ClientBuilder.newClient();
        target = client.target(UriBuilder.fromPath(SERVICE_URL + "/api/v1"));
    }

    public Optional<TenantModel> getTenant(String tenantId) {
        Response response =
            target.path("/" + tenantId + "/" + tenantId)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);

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
