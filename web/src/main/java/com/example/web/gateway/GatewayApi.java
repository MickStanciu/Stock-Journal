package com.example.web.gateway;

import com.example.common.rest.dto.ErrorDto;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Stateless
public class GatewayApi {

    private static final Logger log = Logger.getLogger(GatewayApi.class);
    private static final String SERVICE_URL = "http://gatewayapi:8080/api";
    private GatewayApiInterface proxy;

    public GatewayApi() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(SERVICE_URL));
        proxy = target.proxy(GatewayApiInterface.class);
    }


    public AuthToken authenticate(String tenantId, String name, String password) {
        Response response = proxy.authenticate(tenantId, name, password);

        //todo: find a way to add the generic to it
        ResponseEnvelope envelope = response.readEntity(ResponseEnvelope.class);
        response.close();

        if (response.getStatus() != 200) {
            processErrors(envelope.getErrors());
        }

        if (envelope.getData() != null) {
            return new AuthToken((String) envelope.getData());
        }
        return null;
    }

    private void processErrors(List<ErrorDto> errors) {
        for (ErrorDto error : errors) {
            log.error(error);
        }
    }
}

