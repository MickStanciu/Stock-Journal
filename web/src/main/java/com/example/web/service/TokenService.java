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

    public String getGwCookieToken() throws WebException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> cookies = context.getRequestCookieMap();
        if (cookies.containsKey("GW")) {
            Cookie cookie = (Cookie) cookies.get("GW");
            return cookie.getValue();
        } else {
            throw new WebException(ExceptionCode.MISSING_COOKIE);
        }
    }

    public TokenClaims getTokenClaims(String token) throws WebException {
        Optional<TokenClaims> tokenClaimsOptional = TokenUtil.getTokenClaims(token);
        if (!tokenClaimsOptional.isPresent()) {
            throw new WebException(ExceptionCode.MISSING_TOKEN);
        }

        return tokenClaimsOptional.get();
    }
}
