package com.example.core.statemachine;

import java.util.HashMap;
import java.util.Map;


public class TimeSheetEntryMachine {

    private static Map<State, Map<Event, State>> timeSheetStateMachine = generateMap();
    private static Map<State, Map<Event, State>> generateMap() {
        Map<State, Map<Event, State>> map = new HashMap<>();

        Map<Event, State> filledTransitions = new HashMap<>();
        filledTransitions.put(Event.FILL, State.FILLED);
        map.put(State.NOT_FILLED, filledTransitions);
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
