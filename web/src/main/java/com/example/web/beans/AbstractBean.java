package com.example.web.beans;

import com.example.account.model.AccountModel;
import com.example.common.security.TokenClaims;
import com.example.web.exception.WebException;
import com.example.web.service.AccountService;
import com.example.web.service.TokenService;
import org.apache.log4j.Logger;

import javax.inject.Inject;

public class AbstractBean {

    private static final Logger log = Logger.getLogger(DashboardBean.class);

    @Inject
    private TokenService tokenService;

    @Inject
    private AccountService accountService;

    private AccountModel account;

    public void initAccount() {
        account = loadAccount();
    }


    private AccountModel loadAccount() {
        String tokenRaw;
        TokenClaims tokenClaims;
        try {
            tokenRaw = tokenService.getGwCookieToken();
            tokenClaims = tokenService.getTokenClaims(tokenRaw);
        } catch (WebException e) {
            log.error(e.getMessage());

            //todo: shall we discard cookie?
            return null;
        }

        log.info("Tenant Id:  " + tokenClaims.getTenantId());
        log.info("Account Id: " + tokenClaims.getAccountId());
        log.info("Role Id:    " + tokenClaims.getRoleId());

        AccountModel account;
        try {
            account = accountService.getAccount(tokenRaw, tokenClaims.getAccountId());
            log.info("ACCOUNT LOADED");
        } catch (WebException e) {
            //todo: what to do?
            log.error(e.getMessage());
            return null;
        }
        return account;
    }

    public AccountModel getAccount() {
        return account;
    }
}
