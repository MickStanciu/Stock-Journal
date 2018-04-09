package com.example.web.gateway;

import com.example.common.rest.dto.ErrorDto;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ejb.Stateless;
import javax.swing.text.html.Option;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

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
        ResponseEnvelope<AuthToken> envelope = response.readEntity(new GenericType<ResponseEnvelope<AuthToken>>(){});
        response.close();

        if (response.getStatus() != 200) {
            processErrors(envelope.getErrors());
        }

        if (envelope.getData() != null) {
            return envelope.getData();
        }
        return null;
    }

    public Optional<Account> getAccount(String token, String tenantId, BigInteger accountId) {
        Response response = proxy.account(token, tenantId, accountId);
        ResponseEnvelope<Account> envelope = response.readEntity(new GenericType<ResponseEnvelope<Account>>(){});
        response.close();

        if (response.getStatus() != 200 && envelope.getErrors() != null) {
            processErrors(envelope.getErrors());
        }

        if (response.getStatus() == 200 && envelope.getData() != null) {
            return Optional.of(envelope.getData());
        }

        return Optional.empty();
    }

    private AuthToken processData(String data) {
        return new AuthToken(data);
    }

    private void processErrors(List<ErrorDto> errors) {
        for (ErrorDto error : errors) {
            log.error(error);
        }
    }
}

