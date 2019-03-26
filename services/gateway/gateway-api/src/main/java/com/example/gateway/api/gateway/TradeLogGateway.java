package com.example.gateway.api.gateway;

import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class TradeLogGateway {

    private static final Logger log = LoggerFactory.getLogger(TradeLogGateway.class);

    private final RestTemplate restTemplate;

    private final String API_URL;

    public TradeLogGateway(RestTemplate restTemplate, @Value("${gateway.tradelog.url}") String url) {
        this.restTemplate = restTemplate;
        this.API_URL = url;
    }

    public List<OptionJournalModel> getAllByAccountId(String accountId) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path(accountId);

        ResponseEntity<List<OptionJournalModel>> responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<OptionJournalModel>>() {});
        return responseEntity.getBody();
    }

    private List<OptionJournalModel> getFake() {
        return Collections.emptyList();
    }
}
