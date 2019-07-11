package com.example.tradelog.api.spec.model;

public enum TransactionType {

    SHARE,
    SYNTHETIC_SHARE,
    OPTION,
    DIVIDEND,
    UNKNOWN;

    static public TransactionType lookup(String id) {
        try {
            return TransactionType.valueOf(id);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}
