package com.example.gatewayapi.gateway;

import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.common.util.UriBuilder;
import com.example.gatewayapi.configuration.Property;
import com.example.tenant.model.TenantModel;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Optional;

public class TenantGateway extends AbstractGateway {

    private static final Logger log = LoggerFactory.getLogger(TenantGateway.class);

    @Inject
    @Property("gateway.tenant.address")
    private String SERVICE_URL = "localhost";;

    public Optional<TenantModel> getTenant(String tenantId) {
        UriBuilder builder = UriBuilder.builder(SERVICE_URL)
                .addPath("api")
                .addPath("v1")
                .addPath(tenantId);

        return getTenantModel(builder.build());
    }

    private Optional<TenantModel> getTenantModel(URI uri) {
        ResteasyWebTarget target = this.getTarget(uri);
        ResponseEnvelope<TenantModel> envelope;

        try {
            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);
            envelope = response.readEntity(new GenericType<ResponseEnvelope<TenantModel>>() {});
            response.close();
        } catch (Exception ex) {
            log.error("Hmmm", ex);
            return Optional.empty();
        }

        if (envelope.getErrors() != null) {
            processErrors(envelope.getErrors());
        }

        return Optional.of(envelope.getData());
    }
}
