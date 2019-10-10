package com.example.gateway.api.spec.model;

public enum TransactionTypeGW {

    DIVIDEND,
    SHARE,
    SYNTHETIC_SHARE,
    OPTION,
    UNKNOWN;

    static public TransactionTypeGW lookup(String id) {
        try {
            return TransactionTypeGW.valueOf(id);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}
