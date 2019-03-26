package com.example.web.gateway;

import com.example.gateway.api.model.OptionJournalGWModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class TradeJournalGateway {

    private static final Logger log = LoggerFactory.getLogger(TradeJournalGateway.class);

    private final RestTemplate restTemplate;

    private final String API_URL;

    @Autowired
    public TradeJournalGateway(RestTemplate restTemplate, @Value("${gateway.url}") String url) {
        this.restTemplate = restTemplate;
        this.API_URL = url;
    }

    public List<OptionJournalGWModel> getAllByAccountId(String accountId) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path(accountId);

        ResponseEntity<List<OptionJournalGWModel>> responseEntity = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<OptionJournalGWModel>>() {});
        return responseEntity.getBody();
    }
}
