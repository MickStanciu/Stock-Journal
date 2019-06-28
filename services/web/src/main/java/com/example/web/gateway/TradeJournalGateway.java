package com.example.web.gateway;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.gateway.api.model.TradeLogModelGW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class TradeJournalGateway {

    private static final Logger log = LoggerFactory.getLogger(TradeJournalGateway.class);

    private final RestTemplate restTemplate;
    private final String API_URL;

    public TradeJournalGateway(RestTemplate restTemplate, @Value("${gateway.url}") String url) {
        this.restTemplate = restTemplate;
        this.API_URL = url;
    }

    public TradeLogModelGW getAllTradesBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/all/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<TradeLogModelGW> responseEntity = restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), TradeLogModelGW.class);
        return responseEntity.getBody();
    }

    public List<String> getUniqueSymbols(String accountId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/symbols");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(builder.build(accountId), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<String>>() {});
        return responseEntity.getBody();
    }

    public ShareJournalGWModel createShareTrade(String accountId, ShareJournalGWModel model) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/shares");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<ShareJournalGWModel> request = new HttpEntity<>(model, headers);
        ResponseEntity<ShareJournalGWModel> responseEntity = restTemplate.exchange(builder.build(""), HttpMethod.POST, request, ShareJournalGWModel.class);
        return responseEntity.getBody();
    }

    public OptionJournalGWModel createOptionTrade(String accountId, OptionJournalGWModel model) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/options");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<OptionJournalGWModel> request = new HttpEntity<>(model, headers);
        ResponseEntity<OptionJournalGWModel> responseEntity = restTemplate.exchange(builder.build(""), HttpMethod.POST, request, OptionJournalGWModel.class);
        return responseEntity.getBody();
    }

    public void deleteShareTrade(String accountId, String symbol, String transactionId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/shares/{symbol}/{id}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        restTemplate.exchange(builder.build(symbol, transactionId), HttpMethod.DELETE, new HttpEntity(headers), Object.class);
    }
}
