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
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TimesheetService implements Serializable {

    private static final int SLOT_SIZE = 30; //minutes

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

    public List<TimesheetEntryModel> generateSlots(String tenantId, BigInteger accountId) {
        LocalDateTime fromDate = TimeConversion.getStartOfDay();
        LocalDateTime toDate = TimeConversion.getEndOfDay();
        List<TimesheetEntryModel> timesheetSlots = gatewayApi.getEntries(tenantId, accountId, fromDate, toDate);
        List<TimesheetEntryModel> calendarSlots = new ArrayList<>(48);

        //empty slots
        for (int i = 0; i < 48; i++) {
            calendarSlots.add(i, null);
        }

        //not empty slots
        for (TimesheetEntryModel model : timesheetSlots) {
            int startSlot = getStartSlot(model);
            int endSlot = getEndSlot(model);

            for (int i = startSlot; i <= endSlot; i++) {
                calendarSlots.add(i, model);
            }
        }

        return calendarSlots;
    }

    int getStartSlot(TimesheetEntryModel timesheetEntry) {
        LocalDateTime startDateTime = TimeConversion.fromInstant(timesheetEntry.getFromTime());
        int startMinute = startDateTime.getHour() * 60 + startDateTime.getMinute();
        return startMinute / SLOT_SIZE;
    }

    int getEndSlot(TimesheetEntryModel timesheetEntry) {
        LocalDateTime sendDateTime = TimeConversion.fromInstant(timesheetEntry.getToTime());
        int endMinute = sendDateTime.getHour() * 60 + sendDateTime.getMinute();
        return endMinute / SLOT_SIZE;
    }

}
