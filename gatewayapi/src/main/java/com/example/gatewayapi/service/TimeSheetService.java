package com.example.gatewayapi.service;

import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.gateway.pula;
import com.example.timesheet.model.TimeSheetEntryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.List;

public class TimeSheetService {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetService.class);

    @Inject
    private pula timesheetGateway;

    public List<TimeSheetEntryModel> getTimesheetEntries(String tenantId, BigInteger accountId, String from, String to) throws GatewayApiException {
        List<TimeSheetEntryModel> entryList = timesheetGateway.getTimeSheetEntries(tenantId, accountId, from, to);
        if (entryList == null || entryList.isEmpty()) {
            throw new GatewayApiException(ExceptionCode.TIMESHEET_EMPTY);
        }

        return entryList;
    }

}
