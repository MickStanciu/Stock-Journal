package com.example.gateway.api.gateway;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    private final HttpHeaders headers;

    public TradeLogGateway(RestTemplate restTemplate, @Value("${gateway.tradelog.url}") String url) {
        this.restTemplate = restTemplate;
        this.API_URL = url;
        this.headers = new HttpHeaders();
//        this.headers.setContentType(MediaType.APPLICATION_JSON);
    }


    public List<String> getAllTradedSymbols(String accountId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/transactions/symbols");

        this.headers.set("accountId", accountId);

        ResponseEntity<List<String>> responseEntity = restTemplate
                .exchange(builder.build(""), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<String>>() {});
        return responseEntity.getBody();
    }


    @Async("asyncExecutor")
    public CompletableFuture<List<ShareJournalModel>> getShareTransactionsBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/shares/{symbol}");

        this.headers.set("accountId", accountId);

        ResponseEntity<List<ShareJournalModel>> responseEntity =
                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<ShareJournalModel>>() {});
        return CompletableFuture.completedFuture(responseEntity.getBody());
    }


    @Async("asyncExecutor")
    public CompletableFuture<List<OptionJournalModel>> getOptionTransactionsBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/options/{symbol}");

        this.headers.set("accountId", accountId);

        ResponseEntity<List<OptionJournalModel>> responseEntity =
                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<OptionJournalModel>>() {});
        return CompletableFuture.completedFuture(responseEntity.getBody());
    }


    @Async("asyncExecutor")
    public CompletableFuture<List<DividendJournalModel>> getDividendTransactionsBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/dividends/{symbol}");

        this.headers.set("accountId", accountId);

        ResponseEntity<List<DividendJournalModel>> responseEntity =
                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<DividendJournalModel>>() {});
        return CompletableFuture.completedFuture(responseEntity.getBody());
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
