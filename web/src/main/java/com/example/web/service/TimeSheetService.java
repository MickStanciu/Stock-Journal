package com.example.web.service;

import com.example.common.converter.TimeConversion;
import com.example.core.model.TimeSheetEntryModel;
import com.example.web.configuration.InjectionType;
import com.example.web.gateway.GatewayApi;
import com.example.web.model.TimeSheetSlotModel;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TimeSheetService implements Serializable {

    private static final int SLOT_SIZE = 30; //minutes

    @Inject
    @InjectionType(isMock = true)
    private GatewayApi gatewayApi;

    @PostConstruct
    public void init() {
    }

    public List<TimeSheetEntryModel> getTodayEntries(String tenantId, BigInteger accountId) {
        LocalDateTime fromDate = TimeConversion.getStartOfDay();
        LocalDateTime toDate = TimeConversion.getEndOfDay();
        return gatewayApi.getEntries(tenantId, accountId, fromDate, toDate);
    }

    //todo: maybe in a TimeSheetUtil class?
    public List<TimeSheetSlotModel> generateDailySlots(List<TimeSheetEntryModel> timeSheetSlots) {
        List<TimeSheetSlotModel> calendarSlots = new ArrayList<>(48);
        LocalDateTime begin = TimeConversion.getStartOfDay();

        //empty slots
        for (int i = 0; i < 48; i++) {
            calendarSlots.add(i,
                    TimeSheetSlotModel.builder()
                            .withSlot(i)
                            .fromTime(begin.plusMinutes(30 * i))
                            .toTime(begin.plusMinutes(30 * (i+1)).minusMinutes(1))
                            .build());
        }

        //not empty slots
        for (TimeSheetEntryModel model : timeSheetSlots) {
            int startSlot = getStartSlot(model);
            int endSlot = getEndSlot(model);

            for (int i = startSlot; i <= endSlot && startSlot >= 0 && endSlot < 48; i++) {
                calendarSlots.remove(i);
                calendarSlots.add(i, TimeSheetSlotModel.builder()
                        .withSlot(i)
                        .fromTime(begin.plusMinutes(30 * i))
                        .toTime(begin.plusMinutes(30 * (i+1)).minusMinutes(1))
                        .withEntry(model)
                        .build());
            }
        }


        return calendarSlots;
    }

    int getStartSlot(TimeSheetEntryModel timeSheetEntry) {
        LocalDateTime startDateTime = TimeConversion.fromInstant(timeSheetEntry.getFromTime());
        int startMinute = startDateTime.getHour() * 60 + startDateTime.getMinute();
        return startMinute / SLOT_SIZE;
    }

    int getEndSlot(TimeSheetEntryModel timeSheetEntry) {
        LocalDateTime sendDateTime = TimeConversion.fromInstant(timeSheetEntry.getToTime());
        int endMinute = sendDateTime.getHour() * 60 + sendDateTime.getMinute();
        return endMinute / SLOT_SIZE;
    }

}
