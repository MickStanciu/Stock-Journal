package com.example.core.validation;

import com.example.common.validator.FieldValidator;

import java.math.BigInteger;

public class RequestValidation extends FieldValidator {


    public static boolean validateGetJobs(String tenantId, BigInteger accountId) {
        return RequestValidation.tenantId(tenantId) && RequestValidation.accountId(accountId);
    }
}
