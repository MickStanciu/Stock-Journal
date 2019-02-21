package com.example.tradelog.api.spec.model;

public enum ActionType {
    UNKNOWN,
    CASH_SECURED_PUT;

    static public ActionType lookup(String id) {
        try {
            return ActionType.valueOf(id);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}
