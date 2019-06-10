package com.example.gateway.api.gateway;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.tradelog.api.spec.model.TradeLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    public TradeLogModel getAllBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/{accountId}/trades/{symbol}");

        ResponseEntity<TradeLogModel> responseEntity = restTemplate.exchange(builder.build(accountId, symbol), HttpMethod.GET, null, TradeLogModel.class);
        return responseEntity.getBody();
    }

    public List<String> getAllTradedSymbols(String accountId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/{accountId}/symbols");

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(builder.build(accountId), HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
        return responseEntity.getBody();
    }

    public ShareJournalGWModel createShareTrade(String accountId, ShareJournalGWModel model) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/{accountId}/share");

        HttpEntity<ShareJournalGWModel> request = new HttpEntity<>(model);
        ResponseEntity<ShareJournalGWModel> responseEntity = restTemplate.exchange(builder.build(accountId), HttpMethod.POST, request, ShareJournalGWModel.class);
        return responseEntity.getBody();
    }

    public OptionJournalGWModel createOptionTrade(String accountId, OptionJournalGWModel model) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/{accountId}/option");

        HttpEntity<OptionJournalGWModel> request = new HttpEntity<>(model);
        ResponseEntity<OptionJournalGWModel> responseEntity = restTemplate.exchange(builder.build(accountId), HttpMethod.POST, request, OptionJournalGWModel.class);
        return responseEntity.getBody();
    }
}
