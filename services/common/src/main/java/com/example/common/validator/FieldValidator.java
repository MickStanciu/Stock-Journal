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
            .notEmpty()
            .sizeGreaterOrEqualTo(1)
            .sizeLessOrEqualTo(6)
            .isValid();

    protected static Predicate<Integer> limit = n -> new IntegerValidator(n)
            .notNull()
            .greaterThanZero()
            .isValid();
}
