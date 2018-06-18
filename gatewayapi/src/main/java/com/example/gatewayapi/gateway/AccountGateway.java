package com.example.gatewayapi.gateway;

import com.example.account.model.AccountModel;
import com.example.common.rest.envelope.ResponseEnvelope;
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

    @Value("${gateway.account.address}")
    private String SERVICE_URL;

    private WebTarget target;

    @PostConstruct
    public void init() {
        Client client = ClientBuilder.newClient();
        target = client.target(UriBuilder.fromPath(SERVICE_URL + "/api/v1"));
    }

    public Optional<AccountModel> getAccount(String tenantId, BigInteger accountId) {
        Response response =
                target.path("/" + tenantId + "/" + accountId)
                        .request(MediaType.APPLICATION_JSON)
                        .get(Response.class);

        return getAccountModel(response);
    }



    public Optional<AccountModel> getAccount(String tenantId, String email, String password) {
        Response response =
                target.path(String.format("/%s?email=%s&password=%s", tenantId, email, password))
                        .request(MediaType.APPLICATION_JSON)
                        .get(Response.class);

        return getAccountModel(response);
    }


    private Optional<AccountModel> getAccountModel(Response response) {
        ResponseEnvelope<AccountModel> envelope = response.readEntity(new GenericType<ResponseEnvelope<AccountModel>>(){});
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
