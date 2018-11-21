package com.example.timesheet.api.rest;

import com.example.common.validator.FieldValidator;
import com.example.common.validator.StringValidator;

public class RequestValidation extends FieldValidator {

    private static boolean date(String date) {
        return new StringValidator(date)
                .notNull()
                .isValid();
        //todo: build up on this one  //todo: check regex for date
    }
    public static boolean validateGetJobs(String tenantId, long accountId) {
        return RequestValidation.tenantId(tenantId) && RequestValidation.accountId(accountId);
    }

    public static boolean validateGetTimesheet(String tenantId, long accountId, String from, String to) {
        return RequestValidation.tenantId(tenantId) && RequestValidation.accountId(accountId);
        //todo: make sure from  <  to
    }


}
