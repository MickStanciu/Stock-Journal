package com.example.web.service;

import com.example.common.converter.TimeConversion;
import com.example.core.model.TimesheetEntryModel;
import com.example.web.configuration.InjectionType;
import com.example.web.gateway.GatewayApi;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TimesheetService implements Serializable {

    @Inject
    @InjectionType(isMock = true)
    private GatewayApi gatewayApi;

    @PostConstruct
    public void init() {
    }

    public List<TimesheetEntryModel> getTodayEntries(String tenantId, BigInteger accountId) {
        LocalDateTime fromDate = TimeConversion.getStartOfDay();
        LocalDateTime toDate = TimeConversion.getEndOfDay();
        return gatewayApi.getEntries(tenantId, accountId, fromDate, toDate);
    }

}
