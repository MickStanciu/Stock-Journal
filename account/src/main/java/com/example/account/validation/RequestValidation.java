package com.example.account.validation;

import com.example.common.validator.StringValidator;

public class RequestValidation {

    public static boolean tenant(String tenantId) {
        return new StringValidator(tenantId)
                .notNull()
                .sizeEqualTo(36)
                .isValid();
    }

    public static boolean accountName(String name) {
        return new StringValidator(name)
                .notNull()
                .sizeLessOrEqualTo(64)
                .isValid();
    }

    public static boolean accountPassword(String password) {
        return new StringValidator(password)
                .notNull()
                .sizeLessOrEqualTo(64)
                .isValid();
    }
}
