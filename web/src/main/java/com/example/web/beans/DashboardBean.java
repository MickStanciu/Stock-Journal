package com.example.web.beans;

import com.example.account.model.AccountModel;
import com.example.common.converter.TimeConversion;
import com.example.common.security.TokenClaims;
import com.example.web.exception.WebException;
import com.example.web.service.AccountService;
import com.example.web.service.TokenService;
import org.apache.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;

@Named("DashboardBean")
@RequestScoped
public class DashboardBean implements Serializable {

    private static final Logger log = Logger.getLogger(DashboardBean.class);

    @ManagedProperty("#{SessionBean.account}")
    private AccountModel account;

    @Inject
    private AccountService accountService;

    @Inject
    private TokenService tokenService;

    public void onLoad() {
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
                account = accountService.getAccount(tokenRaw, tokenClaims.getAccountId());
                log.info("ACCOUNT LOADED");
            } catch (WebException e) {
                //todo: what to do?
                log.error(e.getMessage());
            }
        } else {
            log.warn("ACCOUNT ALREADY LOADED");
        }

    }

    //todo: can use facelets converter now
    private String getTodayDateFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MMM-yyyy");
        return formatter.format(TimeConversion.getStartOfDay());
    }

    public AccountModel getAccount() {
        return account;
    }
}
