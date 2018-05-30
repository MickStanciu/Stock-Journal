package com.example.core.statemachine;


import java.util.HashMap;
import java.util.Map;

//? visitor patter? listener?

interface TimeSheetState {
    TimeSheetState fillTimeSheet();
}


class NotFilled implements TimeSheetState {

    @Override
    public TimeSheetState fillTimeSheet() {
        return new Filled();
    }
}

class Filled implements TimeSheetState {
    private State state = State.FILLED;

    @Override
    public TimeSheetState fillTimeSheet() {
        return this;
    }
}

class Context implements TimeSheetState {
    private TimeSheetState timeSheetState;


    @Override
    public TimeSheetState fillTimeSheet() {
        return timeSheetState.fillTimeSheet();
    }


    public Context() {
        setTimeSheetState(new NotFilled());
    }


    public void setTimeSheetState(TimeSheetState timeSheetState) {
        this.timeSheetState = timeSheetState;
    }

    public TimeSheetState getTimeSheetState() {
        return timeSheetState;
    }

}

public class StateDemo {
    private static Map<State, Map<Event, State>> timeSheetStateMachine = generateMap();

    private static Map<State, Map<Event, State>> generateMap() {
        Map<State, Map<Event, State>> map = new HashMap<>();

        //map.put(State.NOT_FILLED, );
        return map;
    }

    public static void main(String[] args) {
        Context context = new Context();
        System.out.println("Initial State: " + context.getTimeSheetState().toString());

        context.fillTimeSheet();
        System.out.println("Current State: " + context.getTimeSheetState().toString());
    }

}
