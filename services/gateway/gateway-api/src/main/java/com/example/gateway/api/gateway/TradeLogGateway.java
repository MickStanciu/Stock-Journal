package com.example.gateway.api.gateway;

import com.example.gateway.api.spec.model.ShareJournalGWModel;
import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.DividendTransactionsResponse;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.OptionTransactionsResponse;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.spec.model.ShareTransactionsResponse;
import com.example.tradelog.api.spec.model.TradeSummaryModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
    public CompletableFuture<ShareTransactionsResponse> getShareTransactionsBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/shares/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<ShareTransactionsResponse> responseEntity =
                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), ShareTransactionsResponse.class);
        return CompletableFuture.completedFuture(responseEntity.getBody());
    }


    @Async("asyncExecutor")
    public CompletableFuture<OptionTransactionsResponse> getOptionTransactionsBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/options/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<OptionTransactionsResponse> responseEntity =
                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), OptionTransactionsResponse.class);
        return CompletableFuture.completedFuture(responseEntity.getBody());
    }


    @Async("asyncExecutor")
    public CompletableFuture<DividendTransactionsResponse> getDividendTransactionsBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/dividends/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<DividendTransactionsResponse> responseEntity =
                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), DividendTransactionsResponse.class);
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
        ResponseEntity<ShareJournalModel> responseEntity = restTemplate.exchange(builder.build(model.getTransactionDetails().getSymbol()), HttpMethod.POST, request, ShareJournalModel.class);
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
        ResponseEntity<OptionJournalModel> responseEntity = restTemplate.exchange(builder.build(model.getTransactionDetails().getSymbol()), HttpMethod.POST, request, OptionJournalModel.class);
        return responseEntity.getBody();
    }

    public DividendJournalModel createDividendTrade(String accountId, DividendJournalModel model) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/dividends");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<DividendJournalModel> request = new HttpEntity<>(model, headers);
        ResponseEntity<DividendJournalModel> responseEntity = restTemplate.exchange(builder.build(model.getTransactionDetails().getSymbol()), HttpMethod.POST, request, DividendJournalModel.class);
        return responseEntity.getBody();
    }

    public void updateShareTrade(String accountId, String transactionId, ShareJournalModel model) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/shares/{id}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<ShareJournalModel> request = new HttpEntity<>(model, headers);
        restTemplate.exchange(builder.build(transactionId), HttpMethod.PUT, request, Object.class);
    }

    public void deleteShareTrade(String accountId, String transactionId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/shares/{id}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        restTemplate.exchange(builder.build(transactionId), HttpMethod.DELETE, new HttpEntity(headers), Object.class);
    }

    public void deleteOptionTrade(String accountId, String transactionId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/options/{id}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        restTemplate.exchange(builder.build(transactionId), HttpMethod.DELETE, new HttpEntity(headers), Object.class);
    }

    public void deleteDividendTrade(String accountId, String transactionId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/dividends/{id}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        restTemplate.exchange(builder.build(transactionId), HttpMethod.DELETE, new HttpEntity(headers), Object.class);
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
