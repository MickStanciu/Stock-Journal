package com.example.gateway.api.rest;

import com.example.common.security.TokenClaims;
import com.example.common.security.TokenUtil;
import com.example.gateway.api.exception.ExceptionCode;
import com.example.gateway.api.exception.GatewayApiException;

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
