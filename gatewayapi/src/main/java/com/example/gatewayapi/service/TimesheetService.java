package com.example.gatewayapi.service;

import com.example.account.model.AccountModel;
import com.example.core.model.TimesheetEntryModel;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.gateway.AccountGateway;
import com.example.gatewayapi.gateway.TimesheetGateway;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Stateless
public class TimesheetService {

    private static final Logger log = Logger.getLogger(TimesheetService.class);

    @Inject
    private AccountGateway accountGateway;

    @Inject
    private TimesheetGateway timesheetGateway;

    public AccountModel getAccount(String tenantId, BigInteger accountId) throws GatewayApiException {
        Optional<AccountModel> accountOptional = accountGateway.getAccount(tenantId, accountId);
        if (!accountOptional.isPresent()) {
            throw new GatewayApiException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return accountOptional.get();
    }


    public List<TimesheetEntryModel> getTimesheetEntries(String tenantId, BigInteger accountId, String from, String to) throws GatewayApiException {
        List<TimesheetEntryModel> entryList = timesheetGateway.getTimesheetEntries(tenantId, accountId, from, to);
        if (entryList == null || entryList.isEmpty()) {
            throw new GatewayApiException(ExceptionCode.TIMESHEET_EMPTY);
        }

        return entryList;
    }
}
