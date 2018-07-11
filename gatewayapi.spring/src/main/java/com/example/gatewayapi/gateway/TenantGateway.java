package com.example.gatewayapi.gateway;

import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.tenant.model.TenantModel;
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

import java.net.URI;
import java.util.Optional;

@Component
public class TenantGateway extends AbstractGateway {

    private static final Logger log = LoggerFactory.getLogger(TenantGateway.class);

    @Value("${gateway.tenant.address}")
    private String SERVICE_URL;

    @Autowired
    public TenantGateway(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public Optional<TenantModel> getTenant(String tenantId) {
        String pathTemplate = SERVICE_URL + "/api/v1/{tenantId}";
        URI uri = UriComponentsBuilder
                .fromUriString(pathTemplate)
                .build(tenantId);

        return getTenantModel(uri);
    }

    private Optional<TenantModel> getTenantModel(URI uri) {
        ResponseEntity<ResponseEnvelope<TenantModel>> response;

        try {
            response = getRestTemplate().exchange(uri, HttpMethod.GET, null,
                        new ParameterizedTypeReference<ResponseEnvelope<TenantModel>>() {}
                );
        } catch (HttpStatusCodeException ex) {
            log.error("ReferralRockResponseModel Rock API failed with code {}", ex.getStatusCode().toString(), ex);
            return Optional.empty();
        } catch (RestClientException ex) {
            log.error("Rest client exception", ex);
            return Optional.empty();
        }

        ResponseEnvelope<TenantModel> envelope = response.getBody();

        if (envelope.getErrors() != null) {
            processErrors(envelope.getErrors());
        }

        return Optional.of(envelope.getData());
    }

}
