package com.example.gatewayapi.gateway;

import com.example.account.model.AccountModel;
import com.example.common.rest.envelope.ResponseEnvelope;
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
import java.math.BigInteger;
import java.util.Optional;

@Component
public class AccountGateway extends AbstractGateway {

    private static final Logger log = LoggerFactory.getLogger(AccountGateway.class);

    @Value("${gateway.account.address}")
    private String SERVICE_URL;

    private WebTarget target;

    @PostConstruct
    public void init() {
        Client client = ClientBuilder.newClient();
        client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
        client.property(ClientProperties.READ_TIMEOUT,    1000);
        target = client.target(UriBuilder.fromPath(SERVICE_URL + "/api/v1"));
    }

    public Optional<AccountModel> getAccount(String tenantId, BigInteger accountId) {
        Response response =
                target.path("/" + tenantId + "/" + accountId)
                        .request(MediaType.APPLICATION_JSON)
                        .get(Response.class);

        return getModel(response);
    }



    public Optional<AccountModel> getAccount(String tenantId, String email, String password) {
        Response response =
                target.path("/" + tenantId)
                        .queryParam("email", email)
                        .queryParam("password", password)
                        .request(MediaType.APPLICATION_JSON)
                        .get(Response.class);

        return getModel(response);
    }

    private Optional<AccountModel> getModel(Response response) {
        ResponseEnvelope<AccountModel> envelope;
        try {
            envelope = response.readEntity(new GenericType<ResponseEnvelope<AccountModel>>(){});
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
