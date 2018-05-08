package com.example.web.gateway;

import com.example.account.model.AccountModel;
import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.gatewayapi.model.AuthTokenModel;
import com.example.gatewayapi.rest.AccountRestInterface;
import com.example.gatewayapi.rest.AuthenticationRestInterface;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Stateless
public class GatewayApi {

    @Inject
    @SystemProperty("GATEWAY_API_ADDRESS")
    private String SERVICE_URL;

    private static final Logger log = Logger.getLogger(GatewayApi.class);
    private AuthenticationRestInterface authProxy;
    private AccountRestInterface accountProxy;

    public GatewayApi() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(SERVICE_URL));
        authProxy = target.proxy(AuthenticationRestInterface.class);
        accountProxy = target.proxy(AccountRestInterface.class);
    }

    public AuthTokenModel authenticate(String tenantId, String name, String password) {
        Response response = authProxy.authenticate(tenantId, name, password);
        ResponseEnvelope<AuthTokenModel> envelope = response.readEntity(new GenericType<ResponseEnvelope<AuthTokenModel>>(){});
        response.close();

        if (response.getStatus() != 200) {
            processErrors(envelope.getErrors());
        }

        if (envelope.getData() != null) {
            return envelope.getData();
        }
        return null;
    }

    public Optional<AccountModel> getAccount(String token, BigInteger accountId) {
        Response response = accountProxy.getAccountDetails(token, accountId);
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

    private void processErrors(List<ErrorDto> errors) {
        for (ErrorDto error : errors) {
            log.error(error);
        }
    }
}

