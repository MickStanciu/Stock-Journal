package com.example.web.service;


import com.example.common.converter.TimeConversion;
import com.example.core.model.TimeSheetEntryModel;
import com.example.web.gateway.GatewayApi;
import com.example.web.gateway.GatewayApiMock;
import com.example.web.model.TimeSheetSlotModel;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class TimeSheetServiceTest {

    @Mock
    private GatewayApi gatewayApi;

    @InjectMocks
    private final TimeSheetService timeSheetService = new TimeSheetService();

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        GatewayApiMock gatewayApiMock = new GatewayApiMock();

        when(gatewayApi.getEntries(anyString(), any(BigInteger.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(gatewayApiMock.getEntries("123", BigInteger.ONE, TimeConversion.getStartOfDay(), TimeConversion.getEndOfDay()));
    }

    @Test
    public void testStartSlot() {
        TimeSheetEntryModel model = TimeSheetEntryModel.builder()
                .fromTime(TimeConversion.getStartOfDay().plusHours(8).toInstant(ZoneOffset.UTC))
                .toTime(TimeConversion.getStartOfDay().plusHours(10).plusMinutes(29).toInstant(ZoneOffset.UTC))
                .build();

        int start = timeSheetService.getStartSlot(model);
        assertEquals(start, 16, "Wrong slot,");
    }

    @Test
    public void testEndSlot() {
        TimeSheetEntryModel model = TimeSheetEntryModel.builder()
                .fromTime(TimeConversion.getStartOfDay().plusHours(8).toInstant(ZoneOffset.UTC))
                .toTime(TimeConversion.getStartOfDay().plusHours(10).plusMinutes(29).toInstant(ZoneOffset.UTC))
                .build();

        int end = timeSheetService.getEndSlot(model);
        assertEquals(end, 20, "Wrong slot,");
    }

    @Test
    public void testSlotAllocation() {
        LocalDateTime fromDate = TimeConversion.getStartOfDay();
        LocalDateTime toDate = TimeConversion.getEndOfDay();
        List<TimeSheetEntryModel> timesheetSlots = gatewayApi.getEntries("123", BigInteger.ONE, fromDate, toDate);

        List<TimeSheetEntryModel> slots = timeSheetService.generateDaySlots(timesheetSlots);
        assertEquals(slots.get(35).getProject().getTitle(), "Slot 35");
//        for (int slotNumber=0; slotNumber<slots.size(); slotNumber++) {
//            TimeSheetEntryModel model = slots.get(slotNumber);
//            System.out.println(slotNumber + " -> " + (model != null ? model.getProject().getTitle() + " " + model.getTask().getTitle() : "empty slot"));
//        }
    }

    @Test
    public void testSlotGeneration() {
        LocalDateTime fromDate = TimeConversion.getStartOfDay();
        LocalDateTime toDate = TimeConversion.getEndOfDay();
        List<TimeSheetEntryModel> timesheetSlots = gatewayApi.getEntries("123", BigInteger.ONE, fromDate, toDate);

        List<TimeSheetSlotModel> slots = timeSheetService.generateDailySlots(timesheetSlots);
        assertEquals(slots.get(0).getFrom().toLocalTime().toString(), "00:00");
        assertEquals(slots.get(47).getFrom().toLocalTime().toString(), "23:30");
    }
}
