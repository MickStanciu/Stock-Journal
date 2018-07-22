package com.example.common.validator;

public class FieldValidator {
    protected static boolean tenantId(String tenantId) {
        return new StringValidator(tenantId)
                .notNull()
                .sizeEqualTo(36)
                .isValid();
    }

    protected static boolean accountId(long accountId) {
        return new LongValidator(accountId)
                .greaterThanZero()
                .isValid();
    }
}
