package com.example.web.service;


import com.example.common.converter.TimeConversion;
import com.example.core.model.TimesheetEntryModel;
import com.example.web.gateway.GatewayApi;
import com.example.web.gateway.GatewayApiMock;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class TimesheetServiceTest {

    @Mock
    private GatewayApi gatewayApi;

    @InjectMocks
    private final TimesheetService timesheetService = new TimesheetService();

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        GatewayApiMock gatewayApiMock = new GatewayApiMock();

        when(gatewayApi.getEntries(anyString(), any(BigInteger.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(gatewayApiMock.getEntries("123", BigInteger.ONE, TimeConversion.getStartOfDay(), TimeConversion.getEndOfDay()));
    }

    @Test
    public void testStartSlot() {
        TimesheetEntryModel model = TimesheetEntryModel.builder()
                .fromTime(TimeConversion.getStartOfDay().plusHours(8).toInstant(ZoneOffset.UTC))
                .toTime(TimeConversion.getStartOfDay().plusHours(10).plusMinutes(29).toInstant(ZoneOffset.UTC))
                .build();

        int start = timesheetService.getStartSlot(model);
        assertEquals(start, 16, "Wrong slot,");
    }

    @Test
    public void testEndSlot() {
        TimesheetEntryModel model = TimesheetEntryModel.builder()
                .fromTime(TimeConversion.getStartOfDay().plusHours(8).toInstant(ZoneOffset.UTC))
                .toTime(TimeConversion.getStartOfDay().plusHours(10).plusMinutes(29).toInstant(ZoneOffset.UTC))
                .build();

        int end = timesheetService.getEndSlot(model);
        assertEquals(end, 20, "Wrong slot,");
    }

//    @Test
//    public void plm() {
//        Map<Integer, TimesheetEntryModel> slots = timesheetService.generateSlots("123", BigInteger.ONE);
//        for (Integer slotNumber : slots.keySet()) {
//            TimesheetEntryModel model = slots.get(slotNumber);
//            System.out.println(slotNumber + " -> " + (model != null ? model.getProject().getTitle() + " " + model.getTask().getTitle() : "empty slot"));
//        }
//    }
}
