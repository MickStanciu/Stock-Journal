package com.example.gateway.accountApi;

import com.example.gateway.ResponseEnvelope;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ejb.Stateless;
import javax.ws.rs.core.UriBuilder;

@Stateless
public class AccountGateway {

    private static final String SERVICE_URL = "http://accountapi:8080";
    private AccountInterface proxy;

    public AccountGateway() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(SERVICE_URL));
        proxy = target.proxy(AccountInterface.class);
    }

    public Account getAccount(String tenantId, String name, String password) {
        ResponseEnvelope<Account> accountResponseEnvelope = proxy.accountByNameAndPassword(tenantId, name, password);
        return accountResponseEnvelope.getData();
    }
}
