package com.example.gateway.api.controller;

import com.example.gateway.api.spec.exception.ExceptionCode;
import com.example.gateway.api.spec.exception.GatewayApiException;
import com.example.gateway.api.security.TokenClaims;
import com.example.gateway.api.security.TokenUtil;

import java.util.Optional;

abstract class AbstractResource {
    String getTenantId(String token) throws GatewayApiException {
        Optional<TokenClaims> tokenClaimsOptional = TokenUtil.getTokenClaims(token);
        if (!tokenClaimsOptional.isPresent()) {
            throw new GatewayApiException(ExceptionCode.REQUEST_NOT_AUTHORIZED);
        }

        return tokenClaimsOptional.get().getTenantId();
    }
}
