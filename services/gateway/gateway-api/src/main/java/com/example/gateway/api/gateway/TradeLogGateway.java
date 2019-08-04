package com.example.gateway.api.gateway;

import com.example.tradelog.api.spec.model.*;
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
                .path("/shares/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<ShareJournalModel> request = new HttpEntity<>(model, headers);
        ResponseEntity<ShareJournalModel> responseEntity = restTemplate.exchange(builder.build(model.getTransactionDetails().getSymbol()), HttpMethod.POST, request, ShareJournalModel.class);
        return responseEntity.getBody();
    }

    public OptionJournalModel createOptionTrade(String accountId, OptionJournalModel model) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/options/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<OptionJournalModel> request = new HttpEntity<>(model, headers);
        ResponseEntity<OptionJournalModel> responseEntity = restTemplate.exchange(builder.build(model.getTransactionDetails().getSymbol()), HttpMethod.POST, request, OptionJournalModel.class);
        return responseEntity.getBody();
    }

    public DividendJournalModel createDividendTrade(String accountId, DividendJournalModel model) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/dividends/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<DividendJournalModel> request = new HttpEntity<>(model, headers);
        ResponseEntity<DividendJournalModel> responseEntity = restTemplate.exchange(builder.build(model.getTransactionDetails().getSymbol()), HttpMethod.POST, request, DividendJournalModel.class);
        return responseEntity.getBody();
    }

    public void deleteShareTrade(String accountId, String transactionId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/shares/{symbol}/{id}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        restTemplate.exchange(builder.build(symbol, transactionId), HttpMethod.DELETE, new HttpEntity(headers), Object.class);
    }

    public void deleteOptionTrade(String accountId, String transactionId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/options/{symbol}/{id}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        restTemplate.exchange(builder.build(symbol, transactionId), HttpMethod.DELETE, new HttpEntity(headers), Object.class);
    }

    public void deleteDividendTrade(String accountId, String transactionId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/dividends/{symbol}/{id}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        restTemplate.exchange(builder.build(symbol, transactionId), HttpMethod.DELETE, new HttpEntity(headers), Object.class);
    }

    public List<TradeSummaryModel> getSummary(String accountId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/transactions/summary");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<List<TradeSummaryModel>> responseEntity = restTemplate
                .exchange(builder.build(""), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<TradeSummaryModel>>() {});
        return responseEntity.getBody();
    }

    public ShareDataModel getShareDataBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/data/share/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<ShareDataModel> responseEntity =
                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), ShareDataModel.class);
        return responseEntity.getBody();
    }

    public void updateTransactionSettings(String accountId, String transactionId, TransactionSettingsModel optionsModel) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/transactions/options/{id}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<TransactionSettingsModel> request = new HttpEntity<>(optionsModel, headers);
        restTemplate.exchange(builder.build(transactionId), HttpMethod.PUT, request, Object.class);
    }

    public void updateTransactionSettingsBulk(String accountId, List<TransactionSettingsModel> models) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/transactions/settings/bulk");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<List<TransactionSettingsModel>> request = new HttpEntity<>(models, headers);
        restTemplate.exchange(builder.build(""), HttpMethod.PUT, request, Object.class);
    }
}
