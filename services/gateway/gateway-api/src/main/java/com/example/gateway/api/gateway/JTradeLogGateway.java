//package com.example.gateway.api.gateway;
//
//import com.example.tradelog.api.spec.model.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.*;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.concurrent.CompletableFuture;
//
//@Service
//public class JTradeLogGateway {
//
//    private static final Logger log = LoggerFactory.getLogger(JTradeLogGateway.class);
//
//    private final RestTemplate restTemplate;
//    private final String API_URL;
//
//    public JTradeLogGateway(RestTemplate restTemplate, @Value("${gateway.tradelog.url}") String url) {
//        this.restTemplate = restTemplate;
//        this.API_URL = url;
//    }
//
//
//    @Async("asyncExecutor")
//    public CompletableFuture<OptionTransactionsResponse> getOptionTransactionsBySymbol(String accountId, String symbol) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/options/{symbol}");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("accountId", accountId);
//
//        ResponseEntity<OptionTransactionsResponse> responseEntity =
//                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), OptionTransactionsResponse.class);
//        return CompletableFuture.completedFuture(responseEntity.getBody());
//    }
//
//
//    @Async("asyncExecutor")
//    public CompletableFuture<DividendTransactionsResponse> getDividendTransactionsBySymbol(String accountId, String symbol) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/dividends/{symbol}");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("accountId", accountId);
//
//        ResponseEntity<DividendTransactionsResponse> responseEntity =
//                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), DividendTransactionsResponse.class);
//        return CompletableFuture.completedFuture(responseEntity.getBody());
//    }
//
//
//    public ShareJournalModel createShareTrade(String accountId, ShareJournalModel model) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/shares");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("accountId", accountId);
//
//        HttpEntity<ShareJournalModel> request = new HttpEntity<>(model, headers);
//        ResponseEntity<ShareJournalModel> responseEntity = restTemplate.exchange(builder.build(model.getTransactionDetails().getSymbol()), HttpMethod.POST, request, ShareJournalModel.class);
//        return responseEntity.getBody();
//    }
//
//    public OptionJournalModel createOptionTrade(String accountId, OptionJournalModel model) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/options");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("accountId", accountId);
//
//        HttpEntity<OptionJournalModel> request = new HttpEntity<>(model, headers);
//        ResponseEntity<OptionJournalModel> responseEntity = restTemplate.exchange(builder.build(model.getTransactionDetails().getSymbol()), HttpMethod.POST, request, OptionJournalModel.class);
//        return responseEntity.getBody();
//    }
//
//    public DividendJournalModel createDividendTrade(String accountId, DividendJournalModel model) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/dividends");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("accountId", accountId);
//
//        HttpEntity<DividendJournalModel> request = new HttpEntity<>(model, headers);
//        ResponseEntity<DividendJournalModel> responseEntity = restTemplate.exchange(builder.build(model.getTransactionDetails().getSymbol()), HttpMethod.POST, request, DividendJournalModel.class);
//        return responseEntity.getBody();
//    }
//
//    public void updateShareTrade(String accountId, String transactionId, ShareJournalModel model) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/shares/{id}");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("accountId", accountId);
//
//        HttpEntity<ShareJournalModel> request = new HttpEntity<>(model, headers);
//        restTemplate.exchange(builder.build(transactionId), HttpMethod.PUT, request, Object.class);
//    }
//
//    public void deleteShareTrade(String accountId, String transactionId) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/shares/{id}");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("accountId", accountId);
//
//        restTemplate.exchange(builder.build(transactionId), HttpMethod.DELETE, new HttpEntity(headers), Object.class);
//    }
//
//    public void deleteOptionTrade(String accountId, String transactionId) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/options/{id}");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("accountId", accountId);
//
//        restTemplate.exchange(builder.build(transactionId), HttpMethod.DELETE, new HttpEntity(headers), Object.class);
//    }
//
//    public void deleteDividendTrade(String accountId, String transactionId) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/dividends/{id}");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("accountId", accountId);
//
//        restTemplate.exchange(builder.build(transactionId), HttpMethod.DELETE, new HttpEntity(headers), Object.class);
//    }
//
//    public List<TradeSummaryItem> getSummary(String accountId) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/transactions/summary");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("accountId", accountId);
//
//        ResponseEntity<TradeSummaryResponse> responseEntity = restTemplate
//                .exchange(builder.build(""), HttpMethod.GET, new HttpEntity<>(headers), TradeSummaryResponse.class);
//        return Objects.requireNonNull(responseEntity.getBody()).getItemsList();
//    }
//
//    public void updateTransactionSettings(String accountId, String transactionId, TransactionSettingsModel optionsModel) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/transactions/options/{id}");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("accountId", accountId);
//
//        HttpEntity<TransactionSettingsModel> request = new HttpEntity<>(optionsModel, headers);
//        restTemplate.exchange(builder.build(transactionId), HttpMethod.PUT, request, Object.class);
//    }
//
//    public void updateTransactionSettingsBulk(String accountId, List<TransactionSettingsModel> models) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/transactions/settings/bulk");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("accountId", accountId);
//
//        HttpEntity<List<TransactionSettingsModel>> request = new HttpEntity<>(models, headers);
//        restTemplate.exchange(builder.build(""), HttpMethod.PUT, request, Object.class);
//    }
//}
