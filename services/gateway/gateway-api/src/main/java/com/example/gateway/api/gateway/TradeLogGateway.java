package com.example.gateway.api.gateway;

import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TradeLogGateway {

    private static final Logger log = LoggerFactory.getLogger(TradeLogGateway.class);

    private final RestTemplate restTemplate;
    private final String API_URL;

    public TradeLogGateway(RestTemplate restTemplate, @Value("${gateway.tradelog.url}") String url) {
        this.restTemplate = restTemplate;
        this.API_URL = url;
    }


    public List<String> getAllTradedSymbols(String accountId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/transactions/symbols");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<List<String>> responseEntity = restTemplate
                .exchange(builder.build(""), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<String>>() {});
        return responseEntity.getBody();
    }


    @Async("asyncExecutor")
    public CompletableFuture<List<ShareJournalModel>> getShareTransactionsBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/shares/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<List<ShareJournalModel>> responseEntity =
                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<ShareJournalModel>>() {});
        return CompletableFuture.completedFuture(responseEntity.getBody());
    }


    @Async("asyncExecutor")
    public CompletableFuture<List<OptionJournalModel>> getOptionTransactionsBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/options/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<List<OptionJournalModel>> responseEntity =
                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<OptionJournalModel>>() {});
        return CompletableFuture.completedFuture(responseEntity.getBody());
    }


    @Async("asyncExecutor")
    public CompletableFuture<List<DividendJournalModel>> getDividendTransactionsBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/dividends/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<List<DividendJournalModel>> responseEntity =
                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<DividendJournalModel>>() {});
        return CompletableFuture.completedFuture(responseEntity.getBody());
    }


    public ShareJournalModel createShareTrade(String accountId, ShareJournalModel model) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/shares");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<ShareJournalModel> request = new HttpEntity<>(model, headers);
        ResponseEntity<ShareJournalModel> responseEntity = restTemplate.exchange(builder.build(""), HttpMethod.POST, request, ShareJournalModel.class);
        return responseEntity.getBody();
    }

    public OptionJournalModel createOptionTrade(String accountId, OptionJournalModel model) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/options");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<OptionJournalModel> request = new HttpEntity<>(model, headers);
        ResponseEntity<OptionJournalModel> responseEntity = restTemplate.exchange(builder.build(""), HttpMethod.POST, request, OptionJournalModel.class);
        return responseEntity.getBody();
    }
}
