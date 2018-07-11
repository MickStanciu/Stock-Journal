package com.example.gatewayapi.gateway;

import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.core.model.TimeSheetEntryModel;
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
import java.util.Collections;
import java.util.List;

@Component
public class TimesheetGateway extends AbstractGateway {

    private static final Logger log = LoggerFactory.getLogger(TimesheetGateway.class);

    @Value("${gateway.timesheet.address}")
    private String SERVICE_URL;

    @Autowired
    public TimesheetGateway(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public List<TimeSheetEntryModel> getTimesheetEntries(String tenantId, BigInteger accountId, String from, String to) {
        String pathTemplate = SERVICE_URL + "/api/v1/{tenantId}/{accountId}";

        URI uri = UriComponentsBuilder
                .fromUriString(pathTemplate)
                .queryParam("from", from)
                .queryParam("to", to)
                .build(tenantId, accountId);

        return getTimeSheetModel(uri);
    }

    private List<TimeSheetEntryModel> getTimeSheetModel(URI uri) {
        ResponseEntity<ResponseEnvelope<List<TimeSheetEntryModel>>> response;

        try {
            response = getRestTemplate().exchange(uri, HttpMethod.GET, null,
                            new ParameterizedTypeReference<ResponseEnvelope<List<TimeSheetEntryModel>>>() {}
                    );
        } catch (HttpStatusCodeException ex) {
            log.error("ReferralRockResponseModel Rock API failed with code {}", ex.getStatusCode().toString(), ex);
            return Collections.emptyList();
        } catch (RestClientException ex) {
            log.error("Rest client exception", ex);
            return Collections.emptyList();
        }

        ResponseEnvelope<List<TimeSheetEntryModel>> envelope = response.getBody();

        if (envelope.getErrors() != null) {
            processErrors(envelope.getErrors());
        }

        return envelope.getData();
    }

}
