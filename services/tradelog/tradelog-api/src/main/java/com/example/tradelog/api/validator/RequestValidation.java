package com.example.tradelog.api.validator;

import com.example.common.validator.FieldValidator;
import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.ActionType;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.ShareJournalModel;

import java.util.function.Predicate;

public class RequestValidation extends FieldValidator {

    public static Boolean validateGetAllTradedSymbols(String accountId) {
        return RequestValidation.accountId.test(accountId);
    }

    public static boolean validateGetAccountAndSymbol(String accountId, String symbol) {
        return RequestValidation.accountId.test(accountId) && RequestValidation.symbol.test(symbol);
    }

    public static boolean validateCreateNewShareTrade(String accountId, ShareJournalModel model) {
        return RequestValidation.accountId.test(accountId) && RequestValidation.validateShareJournalModel.test(model);
    }

    public static boolean validateCreateNewOptionTrade(String accountId, OptionJournalModel model) {
        return RequestValidation.accountId.test(accountId) && RequestValidation.validateOptionJournalModel.test(model);
    }

    private static Predicate<ShareJournalModel> validateShareJournalModel = s -> RequestValidation.accountId.test(s.getAccountId())
            && s.getAction() != null
            && s.getAction() != Action.UNKNOWN
            && s.getActionType() != null
            && s.getActionType() != ActionType.UNKNOWN
            && s.getDate() != null
            && s.getPrice() >= 0.00
            && s.getQuantity() >= 1
            && symbol.test(s.getSymbol())
            && s.getTransactionId() == null;

    private static Predicate<OptionJournalModel> validateOptionJournalModel = s -> {
        return true;
    };
}
