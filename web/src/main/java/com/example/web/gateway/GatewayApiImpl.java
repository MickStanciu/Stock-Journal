package com.example.web.gateway;

import com.example.account.model.AccountModel;
import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.core.model.TimeSheetEntryModel;
import com.example.gatewayapi.model.AuthTokenModel;
import com.example.gatewayapi.rest.AccountRestInterface;
import com.example.gatewayapi.rest.AuthenticationRestInterface;
import com.example.web.configuration.InjectionType;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Stateless
@InjectionType(isMock = false)
public class GatewayApiImpl implements GatewayApi {

//    @Inject
    @SystemProperty("GATEWAY_API_ADDRESS")
    private String SERVICE_URL;

    private static final Logger log = Logger.getLogger(GatewayApiImpl.class);
    private AuthenticationRestInterface authProxy;
    private AccountRestInterface accountProxy;

    @PostConstruct
    public void init() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(SERVICE_URL + "/api"));
        authProxy = target.proxy(AuthenticationRestInterface.class);
        accountProxy = target.proxy(AccountRestInterface.class);
    }

    @Override
    public AuthTokenModel authenticate(String tenantId, String email, String password) {
        Response response = authProxy.authenticate(tenantId, email, password);
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

    @Override
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

    @Override
    public List<TimeSheetEntryModel> getEntries(String token, BigInteger accountId, LocalDateTime fromDate, LocalDateTime toDate) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    private void processErrors(List<ErrorDto> errors) {
        for (ErrorDto error : errors) {
            log.error(error);
        }
    }
}

