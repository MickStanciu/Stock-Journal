package com.example.gateway.api.gateway;

import com.example.stockdata.api.spec.model.ShareDataResponse;
import com.example.tradelog.api.spec.model.ShareDataModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class StockDataGateway {

    private final RestTemplate restTemplate;
    private final String API_URL;

    public StockDataGateway(RestTemplate restTemplate, @Value("${gateway.stockdata.url}") String API_URL) {
        this.restTemplate = restTemplate;
        this.API_URL = API_URL;
    }

    public ShareDataResponse getShareDataBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/data/share/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<ShareDataResponse> responseEntity =
                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), ShareDataResponse.class);
        return responseEntity.getBody();
    }
}
