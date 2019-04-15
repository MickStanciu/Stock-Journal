package com.example.common.validator;

public class FieldValidator {
    protected static boolean accountId(String accountId) {
        return new StringValidator(accountId)
                .notNull()
                .sizeEqualTo(36)
                .isValid();
    }

    protected static boolean symbol(String symbol) {
        return new StringValidator(symbol)
                .notNull()
                .sizeGreaterOrEqualTo(2)
                .sizeLessOrEqualTo(4)
                .isValid();
    }
}
