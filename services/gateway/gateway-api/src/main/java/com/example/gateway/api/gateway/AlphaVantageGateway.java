//package com.example.gateway.api.gateway;
//
//import com.example.gateway.api.model.alphavantage.GlobalQuoteResponse;
//import com.example.stockdata.api.spec.model.ShareDataResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//
//@Service
//public class AlphaVantageGateway {
//
//    private final RestTemplate restTemplate;
//    private final String API_URL;
//    private final String API_KEY;
//    private final String URL_TEMPLATE = "query?function=GLOBAL_QUOTE&symbol=?&apikey=?&datatype=csv";
//
//    public AlphaVantageGateway(RestTemplate restTemplate,
//                               @Value("${gateway.alphavantage.url}") String API_URL,
//                               @Value("${gateway.alphavantage.key}") String API_KEY) {
//        this.restTemplate = restTemplate;
//        this.API_URL = API_URL;
//        this.API_KEY = API_KEY;
//    }
//
//    public GlobalQuoteResponse getQuoteResponse(String symbol) {
//        ResponseEntity<String> responseEntity =
//                restTemplate.getForEntity(buildUri(symbol, API_KEY), String.class);
//        String csv = responseEntity.getBody();
//        System.out.println(csv);
//
//        return new GlobalQuoteResponse.Builder()
//                .withPrice(123)
//                .withSymbol(symbol)
//                .build();
//    }
//
//    private URI buildUri(String symbol, String apiKey) {
//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromHttpUrl(API_URL)
//                .path("/query")
//                .queryParam("function", "GLOBAL_QUOTE")
//                .queryParam("symbol", symbol)
//                .queryParam("datatype", "csv")
//                .queryParam("apikey", apiKey);
//        return builder.build().toUri();
//    }
//
////symbol,open,high,low,price,volume,latestDay,previousClose,change,changePercent
////MSFT,151.0700,152.2100,150.9100,151.3600,15282906,2019-12-09,151.7500,-0.3900,-0.2570%
//}
