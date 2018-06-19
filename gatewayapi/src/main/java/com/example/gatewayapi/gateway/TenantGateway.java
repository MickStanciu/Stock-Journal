package com.example.gatewayapi.gateway;

import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.tenant.model.TenantModel;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(TenantGateway.class);

    @Value("${gateway.tenant.address}")
    private String SERVICE_URL;

    private WebTarget target;

    @PostConstruct
    public void init() {
        Client client = ClientBuilder.newClient();
        client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
        client.property(ClientProperties.READ_TIMEOUT,    1000);
        target = client.target(UriBuilder.fromPath(SERVICE_URL + "/api/v1"));
    }

    public Optional<TenantModel> getTenant(String tenantId) {
        Response response =
            target.path("/" + tenantId)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);

        return getModel(response);
    }

    private Optional<TenantModel> getModel(Response response) {
        ResponseEnvelope<TenantModel> envelope;
        try {
            envelope = response.readEntity(new GenericType<ResponseEnvelope<TenantModel>>(){});
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return Optional.empty();
        } finally {
            response.close();
        }

        if (response.getStatus() != 200 && envelope.getErrors() != null) {
            processErrors(envelope.getErrors());
        }

        if (response.getStatus() == 200 && envelope.getData() != null) {
            return Optional.of(envelope.getData());
        }
        return Optional.empty();
    }

}
