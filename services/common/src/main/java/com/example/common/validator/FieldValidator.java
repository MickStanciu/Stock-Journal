package com.example.common.validator;

import java.util.function.Predicate;

public class FieldValidator {

    protected static Predicate<String> accountId = s -> new StringValidator(s)
            .notNull()
            .sizeEqualTo(36)
            .isValid();

    protected static Predicate<String> transactionId = s -> new StringValidator(s)
            .notNull()
            .sizeEqualTo(36)
            .isValid();

    protected static Predicate<String> symbol = s -> new StringValidator(s)
            .notNull()
            .sizeGreaterOrEqualTo(2)
            .sizeLessOrEqualTo(4)
            .isValid();
}
