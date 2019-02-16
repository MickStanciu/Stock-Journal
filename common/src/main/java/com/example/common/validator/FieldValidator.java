package com.example.common.validator;

public class FieldValidator {
    protected static boolean accountId(String accountId) {
        return new StringValidator(accountId)
                .notNull()
                .sizeEqualTo(36)
                .isValid();
    }
}
