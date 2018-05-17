package com.example.web.beans;

import com.example.account.model.AccountModel;
import com.example.common.converter.TimeConversion;
import com.example.common.security.TokenClaims;
import com.example.web.exception.WebException;
import com.example.web.model.TimeSheetSlotModel;
import com.example.web.service.AccountService;
import com.example.web.service.TimeSheetService;
import com.example.web.service.TokenService;
import org.apache.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("DashboardBean")
@SessionScoped
public class DashboardBean implements Serializable {

    private static final Logger log = Logger.getLogger(DashboardBean.class);


    @Inject
    private AccountService accountService;

    @Inject
    private TokenService tokenService;

    @Inject
    private TimeSheetService timeSheetService;

    private AccountModel account = null;
    private List<TimeSheetSlotModel> timeSheetSlots;
    private Map<String, String> values = new HashMap<>();

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
                account = accountService.getAccount(tokenRaw, tokenClaims.getAccountId());
                log.info("ACCOUNT LOADED");
            } catch (WebException e) {
                //todo: what to do?
                log.error(e.getMessage());
            }
        } else {
            log.info("ACCOUNT ALREADY LOADED");
        }


//        timeSheetSlots = timeSheetService.generateDaySlots(timeSheetService.getTodayEntries(tokenClaims.getTenantId(), tokenClaims.getAccountId()));
        timeSheetSlots = timeSheetService.generateDailySlots(timeSheetService.getTodayEntries(tokenClaims.getTenantId(), tokenClaims.getAccountId()));
        values.put("TODAY", getTodayDateFormatted());
    }

    private String getTodayDateFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd-MMM-yyyy");
        return formatter.format(TimeConversion.getStartOfDay());
    }


    public AccountModel getAccount() {
        return account;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public List<TimeSheetSlotModel> getTimeSheetSlots() {
        return timeSheetSlots;
    }
}
