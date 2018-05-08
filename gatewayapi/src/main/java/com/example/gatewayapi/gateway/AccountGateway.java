package com.example.gatewayapi.gateway;

import com.example.account.model.AccountModel;
import com.example.account.rest.AccountRestInterface;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.math.BigInteger;
import java.util.Optional;

@Stateless
public class AccountGateway extends AbstractGateway {

    @Inject
    @SystemProperty("ACCOUNT_API_ADDRESS")
    private String SERVICE_URL;

    private AccountRestInterface proxy;

    @PostConstruct
    public void init() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(SERVICE_URL + "/api"));
        proxy = target.proxy(AccountRestInterface.class);
    }

    public Optional<AccountModel> getAccount(String tenantId, String email, String password) {
        Response response = proxy.accountByEmailAndPassword(tenantId, email, password);
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

    public Optional<AccountModel> getAccount(String tenantId, BigInteger accountId) {
        Response response = proxy.accountById(tenantId, accountId);
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
