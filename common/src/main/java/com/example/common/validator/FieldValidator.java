package com.example.common.validator;

import java.math.BigInteger;

public class FieldValidator {
    protected static boolean tenantId(String tenantId) {
        return new StringValidator(tenantId)
                .notNull()
                .sizeEqualTo(36)
                .isValid();
    }

    protected static boolean accountId(BigInteger accountId) {
        if (accountId == null) {
            return false;
        }

        Integer accountIdInt = accountId.intValue();
        return new IntegerValidator(accountIdInt)
                .notNull()
                .greaterThanZero()
                .isValid();
    }
}
