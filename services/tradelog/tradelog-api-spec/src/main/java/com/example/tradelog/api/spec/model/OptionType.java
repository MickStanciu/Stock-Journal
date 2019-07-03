package com.example.tradelog.api.spec.model;

public enum OptionType {
    UNKNOWN,
    PUT,
    CALL;

    static public OptionType lookup(String id) {
        try {
            return OptionType.valueOf(id);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}
