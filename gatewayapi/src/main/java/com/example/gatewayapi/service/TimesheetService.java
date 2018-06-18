package com.example.gatewayapi.service;

import com.example.core.model.TimeSheetEntryModel;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.gateway.TimesheetGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class TimesheetService {

    private static final Logger log = LoggerFactory.getLogger(TimesheetService.class);

    private TimesheetGateway timesheetGateway;

    @Autowired
    public TimesheetService(TimesheetGateway timesheetGateway) {
        this.timesheetGateway = timesheetGateway;
    }

    public List<TimeSheetEntryModel> getTimesheetEntries(String tenantId, BigInteger accountId, String from, String to) throws GatewayApiException {
        List<TimeSheetEntryModel> entryList = timesheetGateway.getTimesheetEntries(tenantId, accountId, from, to);
        if (entryList == null || entryList.isEmpty()) {
            throw new GatewayApiException(ExceptionCode.TIMESHEET_EMPTY);
        }

        return entryList;
    }
}
