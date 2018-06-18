package com.example.gatewayapi.controller;

import com.example.common.security.TokenClaims;
import com.example.common.security.TokenUtil;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;

import java.util.Optional;

abstract class AbstractController {

    String getTenantId(String token) throws GatewayApiException {
        Optional<TokenClaims> tokenClaimsOptional = TokenUtil.getTokenClaims(token);
        if (!tokenClaimsOptional.isPresent()) {
            throw new GatewayApiException(ExceptionCode.REQUEST_NOT_AUTHORIZED);
        }

        return tokenClaimsOptional.get().getTenantId();
    }
}
