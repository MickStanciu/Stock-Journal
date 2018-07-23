package com.example.timesheet.statemachine;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class TimeSheetStateMachineTest {

    private TimeSheetEntryMachine tsm;

    @Test
    public void testInitialState() {
        tsm = new TimeSheetEntryMachine();
        assertEquals(tsm.getState(), State.NOT_FILLED);
    }

    @Test
    public void testInitialStateInvalidEvents() {
        tsm = new TimeSheetEntryMachine();
        tsm.sendEvent(Event.CANCEL);
        assertEquals(tsm.getState(), State.NOT_FILLED);

        tsm.sendEvent(Event.SEND_FOR_APPROVAL);
        assertEquals(tsm.getState(), State.NOT_FILLED);

        tsm.sendEvent(Event.APPROVE);
        assertEquals(tsm.getState(), State.NOT_FILLED);
    }

    @Test
    public void testFilledState() {
        tsm = new TimeSheetEntryMachine();
        tsm.sendEvent(Event.FILL);
        assertEquals(tsm.getState(), State.FILLED);
    }

    @Test
    public void testFilledStateInvalidEvents() {
        tsm = new TimeSheetEntryMachine(State.FILLED);
        tsm.sendEvent(Event.FILL);
        assertEquals(tsm.getState(), State.FILLED);

        tsm.sendEvent(Event.APPROVE);
        assertEquals(tsm.getState(), State.FILLED);

        tsm.sendEvent(Event.CANCEL);
        assertEquals(tsm.getState(), State.FILLED);
    }

    @Test
    public void testSentForApprovalState() {
        tsm = new TimeSheetEntryMachine(State.FILLED);
        tsm.sendEvent(Event.SEND_FOR_APPROVAL);
        assertEquals(tsm.getState(), State.SENT_FOR_APPROVAL);

        tsm = new TimeSheetEntryMachine(State.FILLED);
        tsm.sendEvent(Event.CANCEL);
        assertEquals(tsm.getState(), State.FILLED);
    }

    @Test
    public void testSentForApprovalInvalidEvents() {
        tsm = new TimeSheetEntryMachine(State.SENT_FOR_APPROVAL);
        tsm.sendEvent(Event.FILL);
        assertEquals(tsm.getState(), State.SENT_FOR_APPROVAL);

        tsm.sendEvent(Event.SEND_FOR_APPROVAL);
        assertEquals(tsm.getState(), State.SENT_FOR_APPROVAL);
    }

    @Test
    public void testApprovedState() {
        tsm = new TimeSheetEntryMachine(State.SENT_FOR_APPROVAL);
        tsm.sendEvent(Event.APPROVE);
        assertEquals(tsm.getState(), State.APPROVED);
    }

    @Test
    public void testApprovedInvalidEvents() {
        tsm = new TimeSheetEntryMachine(State.APPROVED);
        tsm.sendEvent(Event.FILL);
        assertEquals(tsm.getState(), State.APPROVED);

        tsm.sendEvent(Event.SEND_FOR_APPROVAL);
        assertEquals(tsm.getState(), State.APPROVED);

        tsm.sendEvent(Event.FILL);
        assertEquals(tsm.getState(), State.APPROVED);

        tsm.sendEvent(Event.CANCEL);
        assertEquals(tsm.getState(), State.APPROVED);
    }
}
