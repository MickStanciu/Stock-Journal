package com.example.gateway.api.gateway;

import com.example.stockdata.api.spec.model.PriceItemResponse;
import com.example.stockdata.api.spec.model.PriceResponse;
import com.example.stockdata.api.spec.model.ShareDataResponse;
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

    public PriceItemResponse getPriceDataBySymbol(String accountId, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(API_URL)
                .path("/price/last-close/{symbol}");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(MediaType.APPLICATION_JSON);
        headers.set("accountId", accountId);

        ResponseEntity<PriceResponse> responseEntity =
                restTemplate.exchange(builder.build(symbol), HttpMethod.GET, new HttpEntity<>(headers), PriceResponse.class);
        return responseEntity.getBody().getPrice(0);
    }
}
