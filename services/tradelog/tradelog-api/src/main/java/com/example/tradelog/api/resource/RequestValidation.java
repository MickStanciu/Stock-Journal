package com.example.tradelog.api.resource;

import com.example.common.validator.FieldValidator;

public class RequestValidation extends FieldValidator {

    static Boolean validateGetAllByAccountId(String accountId) {
        return RequestValidation.accountId(accountId);
    }

    static boolean validateGetAccountAndSymbol(String accountId, String symbol) {
        return RequestValidation.accountId(accountId) && RequestValidation.symbol(symbol);
    }

}
