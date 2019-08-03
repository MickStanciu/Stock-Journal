package com.example.web.gateway;

import com.example.gateway.api.model.*;
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

    public TradeLogGWModel getAllTradesBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/all/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<TradeLogGWModel> responseEntity = restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), TradeLogGWModel.class);
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

    public DividendGWModel createDividendRecord(String accountId, DividendGWModel model) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/dividends");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<DividendGWModel> request = new HttpEntity<>(model, headers);
        ResponseEntity<DividendGWModel> responseEntity = restTemplate.exchange(builder.build(""), HttpMethod.POST, request, DividendGWModel.class);
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

    public void deleteOptionTrade(String accountId, String symbol, String transactionId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/options/{symbol}/{id}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        restTemplate.exchange(builder.build(symbol, transactionId), HttpMethod.DELETE, new HttpEntity(headers), Object.class);
    }

    public List<TradeSummaryGWModel> getSummary(String accountId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/summary");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<List<TradeSummaryGWModel>> responseEntity = restTemplate.exchange(builder.build(accountId), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<TradeSummaryGWModel>>() {});
        return responseEntity.getBody();
    }

    public ShareDataGWModel getShareData(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/shares/data/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<ShareDataGWModel> responseEntity = restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), ShareDataGWModel.class);
        return responseEntity.getBody();
    }

    public void updateSettings(String accountId, List<TransactionSettingsGWModel> transactionSettingsGWModelList) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/transaction/settings/bulk");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        HttpEntity<List<TransactionSettingsGWModel>> request = new HttpEntity<>(transactionSettingsGWModelList, headers);
        restTemplate.exchange(builder.build(""), HttpMethod.PUT, request, Object.class);
    }
}
