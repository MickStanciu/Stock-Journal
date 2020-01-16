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




//}
