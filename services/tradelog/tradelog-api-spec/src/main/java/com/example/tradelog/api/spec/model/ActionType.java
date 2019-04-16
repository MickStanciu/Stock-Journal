package com.example.tradelog.api.spec.model;

public enum ActionType {
    UNKNOWN,
    STOCK,
    PUT_OPTION,
    CALL_OPTION;

    static public ActionType lookup(String id) {
        try {
            return ActionType.valueOf(id);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}
