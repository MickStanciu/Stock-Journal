package com.example.tradelog.api.resource;

import com.example.common.validator.FieldValidator;

public class RequestValidation extends FieldValidator {

    static Boolean validateGetAllByAccountId(String accountId) {
        return RequestValidation.accountId(accountId);
    }
}
