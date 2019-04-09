package com.example.tradelog.api.spec.model;

public enum Action {
    UNKNOWN,
    SELL_OPTION,
    BUY_OPTION;

    static public Action lookup(String id) {
        try {
            return Action.valueOf(id);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}
