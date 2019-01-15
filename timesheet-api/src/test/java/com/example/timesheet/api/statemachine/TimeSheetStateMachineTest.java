package com.example.timesheet.api.statemachine;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TimeSheetStateMachineTest {

    private TimeSheetEntryMachine tsm;

    @Test
    void testInitialState() {
        tsm = new TimeSheetEntryMachine();
        Assertions.assertEquals(State.NOT_FILLED, tsm.getState());
    }

    @Test
    void testInitialStateInvalidEvents() {
        tsm = new TimeSheetEntryMachine();
        tsm.sendEvent(Event.CANCEL);
        Assertions.assertEquals(State.NOT_FILLED, tsm.getState());

        tsm.sendEvent(Event.SEND_FOR_APPROVAL);
        Assertions.assertEquals(State.NOT_FILLED, tsm.getState());

        tsm.sendEvent(Event.APPROVE);
        Assertions.assertEquals(State.NOT_FILLED, tsm.getState());
    }

    @Test
    void testFilledState() {
        tsm = new TimeSheetEntryMachine();
        tsm.sendEvent(Event.FILL);
        Assertions.assertEquals(State.FILLED, tsm.getState());
    }

    @Test
    void testFilledStateInvalidEvents() {
        tsm = new TimeSheetEntryMachine(State.FILLED);
        tsm.sendEvent(Event.FILL);
        Assertions.assertEquals(State.FILLED, tsm.getState());

        tsm.sendEvent(Event.APPROVE);
        Assertions.assertEquals(State.FILLED, tsm.getState());

        tsm.sendEvent(Event.CANCEL);
        Assertions.assertEquals(State.FILLED, tsm.getState());
    }

    @Test
    void testSentForApprovalState() {
        tsm = new TimeSheetEntryMachine(State.FILLED);
        tsm.sendEvent(Event.SEND_FOR_APPROVAL);
        Assertions.assertEquals(State.SENT_FOR_APPROVAL, tsm.getState());

        tsm = new TimeSheetEntryMachine(State.FILLED);
        tsm.sendEvent(Event.CANCEL);
        Assertions.assertEquals(State.FILLED, tsm.getState());
    }

    @Test
    void testSentForApprovalInvalidEvents() {
        tsm = new TimeSheetEntryMachine(State.SENT_FOR_APPROVAL);
        tsm.sendEvent(Event.FILL);
        Assertions.assertEquals(State.SENT_FOR_APPROVAL, tsm.getState());

        tsm.sendEvent(Event.SEND_FOR_APPROVAL);
        Assertions.assertEquals(State.SENT_FOR_APPROVAL, tsm.getState());
    }

    @Test
    void testApprovedState() {
        tsm = new TimeSheetEntryMachine(State.SENT_FOR_APPROVAL);
        tsm.sendEvent(Event.APPROVE);
        Assertions.assertEquals(State.APPROVED, tsm.getState());
    }

    @Test
    void testApprovedInvalidEvents() {
        tsm = new TimeSheetEntryMachine(State.APPROVED);
        tsm.sendEvent(Event.FILL);
        Assertions.assertEquals(State.APPROVED, tsm.getState());

        tsm.sendEvent(Event.SEND_FOR_APPROVAL);
        Assertions.assertEquals(State.APPROVED, tsm.getState());

        tsm.sendEvent(Event.FILL);
        Assertions.assertEquals(State.APPROVED, tsm.getState());

        tsm.sendEvent(Event.CANCEL);
        Assertions.assertEquals(State.APPROVED, tsm.getState());
    }
}
