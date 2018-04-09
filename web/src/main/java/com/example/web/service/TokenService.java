package com.example.web.service;

import com.example.common.security.TokenClaims;
import com.example.common.security.TokenUtil;
import com.example.web.exception.ExceptionCode;
import com.example.web.exception.WebException;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@Stateless
public class TokenService implements Serializable {

    private Optional<String> getGwCookieToken() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> cookies = context.getRequestCookieMap();
        if (cookies.containsKey("GW")) {
            Cookie cookie = (Cookie) cookies.get("GW");
            return Optional.of(cookie.getValue());
            //todo: decode token
        } else {
            return Optional.empty();
        }
    }

    public TokenClaims getTokenClaims() throws WebException {
        Optional<String> cookieOptional = getGwCookieToken();
        if (!cookieOptional.isPresent()) {
            throw new WebException(ExceptionCode.MISSING_COOKIE);
        }

        Optional<TokenClaims> tokenClaimsOptional = TokenUtil.getTokenClaims(cookieOptional.get());
        if (!tokenClaimsOptional.isPresent()) {
            throw new WebException(ExceptionCode.MISSING_TOKEN);
        }

        return tokenClaimsOptional.get();
    }
}
