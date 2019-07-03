package com.example.gateway.api.model;

public enum TransactionTypeGW {
    DIVIDEND, SHARE, OPTION, UNKNOWN;

    static public TransactionTypeGW lookup(String id) {
        try {
            return TransactionTypeGW.valueOf(id);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}
