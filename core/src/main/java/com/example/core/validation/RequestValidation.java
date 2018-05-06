package com.example.core.validation;

import com.example.common.validator.FieldValidator;
import com.example.common.validator.StringValidator;

import java.math.BigInteger;

public class RequestValidation extends FieldValidator {

    private static boolean date(String date) {
        return new StringValidator(date)
                .notNull()
                .isValid();
        //todo: build up on this one
    }
    public static boolean validateGetJobs(String tenantId, BigInteger accountId) {
        return RequestValidation.tenantId(tenantId) && RequestValidation.accountId(accountId);
    }

    public static boolean validateGetTimesheet(String tenantId, BigInteger accountId, String from, String to) {
        boolean response = RequestValidation.tenantId(tenantId) && RequestValidation.accountId(accountId);
        return response && date(from) && date(to);
    }


}
