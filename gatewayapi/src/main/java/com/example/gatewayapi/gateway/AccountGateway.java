package com.example.gatewayapi.gateway;

import com.example.account.model.AccountModel;
import com.example.account.model.RoleInfoModel;
import com.example.account.model.RoleModel;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Component
public class AccountGateway extends AbstractGateway {

    private static final Logger log = LoggerFactory.getLogger(AccountGateway.class);

    @Value("${gateway.account.address}")
    private String SERVICE_URL;

    @Autowired
    public AccountGateway(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public Optional<AccountModel> getAccount(String tenantId, BigInteger accountId) {
//        String pathTemplate = SERVICE_URL + "/api/v1/{tenantId}/{accountId}";
//        URI uri = UriComponentsBuilder
//                .fromUriString(pathTemplate)
//                .build(tenantId, accountId);
//
//        return getAccountModel(uri);

        Set<RoleInfoModel> roles = new HashSet<>();
        roles.add(RoleInfoModel.LOG_IN);
        roles.add(RoleInfoModel.CREATE_ACCOUNT);

        AccountModel model = AccountModel.builder()
                .havingPersonalDetails()
                    .withFlagActive(true)
                    .withTenantId("123")
                    .withEmail("test@example.com")
                    .withName("Mick")
                    .withPassword("123")
                    .withId(BigInteger.ONE)
                .havingRole()
                    .withRoleId(1)
                    .withRole(RoleModel.builder()
                            .withPermissions(roles)
                            .withId(1)
                            .withName("TEST ROLE")
                            .build())
                .build();
        return Optional.of(model);
    }

    public Optional<AccountModel> getAccount(String tenantId, String email, String password) {
        String pathTemplate = SERVICE_URL + "/api/v1/{tenantId}";
        URI uri = UriComponentsBuilder
                .fromUriString(pathTemplate)
                .queryParam("email", email)
                .queryParam("password", password)
                .build(tenantId);

        return getAccountModel(uri);
    }


    private Optional<AccountModel> getAccountModel(URI uri) {
        ResponseEntity<ResponseEnvelope<AccountModel>> response;
        try {
            response = getRestTemplate().exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<ResponseEnvelope<AccountModel>>() {
                    }
            );
        } catch (HttpStatusCodeException ex) {
            log.error("ReferralRockResponseModel Rock API failed with code {}", ex.getStatusCode().toString(), ex);
            return Optional.empty();
        } catch (RestClientException ex) {
            log.error("Rest client exception", ex);
            return Optional.empty();
        }

        ResponseEnvelope<AccountModel> envelope = response.getBody();

        if (envelope.getErrors() != null) {
            processErrors(envelope.getErrors());
        }

        return Optional.of(envelope.getData());
    }

}
