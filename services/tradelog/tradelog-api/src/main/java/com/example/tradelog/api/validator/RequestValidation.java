package com.example.tradelog.api.validator;

import com.example.common.validator.FieldValidator;
import com.example.tradelog.api.spec.model.*;

import java.util.function.Predicate;

public class RequestValidation extends FieldValidator {

    public static Boolean validateGetAllTradedSymbols(String accountId) {
        return RequestValidation.accountId.test(accountId);
    }

    public static boolean validateGetAccountAndSymbol(String accountId, String symbol) {
        return RequestValidation.accountId.test(accountId) && RequestValidation.symbol.test(symbol);
    }

    static Predicate<ShareJournalModel> validateShareJournalModel = s -> s != null
            && s.getTransactionModel() != null
            && s.getTransactionModel().getType().equals(TransactionType.SHARE)
            && s.getTransactionModel().getDate() != null
            && symbol.test(s.getTransactionModel().getSymbol())
            && RequestValidation.accountId.test(s.getTransactionModel().getAccountId())
            && s.getTransactionModel().getId() == null
            && s.getAction() != null
            && s.getAction() != Action.UNKNOWN
            && s.getActionType() != null
            && s.getActionType() == ActionType.STOCK
            && s.getPrice() >= 0.00
            && s.getQuantity() >= 1;

    public static boolean validateCreateNewShareTrade(String accountId, ShareJournalModel model) {
        return RequestValidation.accountId.test(accountId) && RequestValidation.validateShareJournalModel.test(model);
    }

    public static boolean validateCreateNewOptionTrade(String accountId, OptionJournalModel model) {
        return RequestValidation.accountId.test(accountId) && RequestValidation.validateOptionJournalModel.test(model);
    }

    static Predicate<OptionJournalModel> validateOptionJournalModel = s -> s != null
            && s.getTransactionModel() != null
            && s.getTransactionModel().getType().equals(TransactionType.OPTION)
            && s.getTransactionModel().getDate() != null
            && symbol.test(s.getTransactionModel().getSymbol())
            && RequestValidation.accountId.test(s.getTransactionModel().getAccountId())
            && s.getTransactionModel().getId() == null
            && s.getAction() != null
            && s.getAction() != Action.UNKNOWN
            && (s.getActionType() == ActionType.CALL || s.getActionType() == ActionType.PUT)
            && s.getContracts() > 0
            && s.getExpiryDate() != null
            && s.getStockPrice() >= 0.00
            && s.getStrikePrice() > 0;
}
