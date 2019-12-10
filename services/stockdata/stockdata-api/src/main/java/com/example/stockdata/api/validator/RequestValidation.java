package com.example.stockdata.api.validator;

import com.example.common.validator.FieldValidator;
import com.example.stockdata.api.model.ShareDataModel;

import java.util.function.Predicate;

public class RequestValidation extends FieldValidator {

    public static boolean validateGetDataBySymbol(String accountId, String symbol) {
        return RequestValidation.accountId.test(accountId) && RequestValidation.symbol.test(symbol);
    }

    public static boolean validateSetDataBySymbol(String accountId, String symbol, ShareDataModel model) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.symbol.test(symbol)
                && model.getSymbol().equals(symbol)
                && validateShareDataModel.test(model);
    }


    static Predicate<ShareDataModel> validateShareDataModel = s -> s != null
            && s.getSymbol() != null;
}
