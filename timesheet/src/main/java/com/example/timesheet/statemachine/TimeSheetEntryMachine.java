package com.example.timesheet.statemachine;

import com.example.core.statemachine.Event;
import com.example.core.statemachine.State;

import java.util.HashMap;
import java.util.Map;


public class TimeSheetEntryMachine {

    private static Map<State, Map<Event, State>> timeSheetStateMachine = generateMap();
    private static Map<State, Map<Event, State>> generateMap() {
        Map<State, Map<Event, State>> map = new HashMap<>();

        //NOT_FILLED
        Map<Event, State> notFilledTransitions = new HashMap<>();
        notFilledTransitions.put(Event.FILL, State.FILLED);
        map.put(State.NOT_FILLED, notFilledTransitions);


        //FILED
        Map<Event, State> filledTransitions = new HashMap<>();
        filledTransitions.put(Event.SEND_FOR_APPROVAL, State.SENT_FOR_APPROVAL);
        map.put(State.FILLED, filledTransitions);

        //SENT_FOR_APPROVAL
        Map<Event, State> sentForApprovalTransitions = new HashMap<>();
        sentForApprovalTransitions.put(Event.APPROVE, State.APPROVED);
        sentForApprovalTransitions.put(Event.CANCEL, State.FILLED);
        map.put(State.SENT_FOR_APPROVAL, sentForApprovalTransitions);
        return map;
    }

    private State state;

    public TimeSheetEntryMachine() {
        state = State.NOT_FILLED;
    }

    public TimeSheetEntryMachine(State state) {
        this.state = state;
    }

    public void sendEvent(Event event) {
        if (!timeSheetStateMachine.containsKey(state)) {
            return;
        }

        Map<Event, State> ev = timeSheetStateMachine.get(state);
        if (!ev.containsKey(event)) {
            return;
        }

        this.state = ev.get(event);
    }

    public State getState() {
        return state;
    }
}
