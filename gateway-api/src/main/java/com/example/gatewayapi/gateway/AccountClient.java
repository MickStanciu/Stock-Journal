package com.example.gatewayapi.gateway;

import com.example.gatewayapi.rest.AccountResource;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface AccountClient {

    @RequestLine("GET /{tenantId}")
    AccountResource accountByEmailAndPassword(
            @Param("tenantId") String tenantId,
            @QueryMap Map<String, String> queryMap
    );
}
