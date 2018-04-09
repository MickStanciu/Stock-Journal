package com.example.web.beans;

import com.example.common.security.TokenClaims;
import com.example.web.exception.WebException;
import com.example.web.gateway.Account;
import com.example.web.service.AccountService;
import com.example.web.service.TokenService;
import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Optional;

@Named("DashboardBean")
@SessionScoped
public class DashboardBean implements Serializable {

    private static final Logger log = Logger.getLogger(DashboardBean.class);

    @Inject
    private AccountService accountService;

    @Inject
    private TokenService tokenService;

    private Account account = null;

    public void onLoad() {
        System.out.println("ON LOAD");

        String tokenRaw;
        TokenClaims tokenClaims;
        try {
            tokenRaw = tokenService.getGwCookieToken();
            tokenClaims = tokenService.getTokenClaims(tokenRaw);
        } catch (WebException e) {
            log.error(e.getMessage());

            //todo: shall we discard cookie?
            return;
        }

        log.info("Tenant Id:  " + tokenClaims.getTenantId());
        log.info("Account Id: " + tokenClaims.getAccountId());
        log.info("Role Id:    " + tokenClaims.getRoleId());

        if (account == null) {
            try {
                account = accountService.getAccount(tokenRaw, tokenClaims.getTenantId(), tokenClaims.getAccountId());
                log.info("ACCOUNT LOADED");
            } catch (WebException e) {
                log.error(e.getMessage());
            }
        } else {
            log.info("ACCOUNT ALREADY LOADED");
        }
    }

}
