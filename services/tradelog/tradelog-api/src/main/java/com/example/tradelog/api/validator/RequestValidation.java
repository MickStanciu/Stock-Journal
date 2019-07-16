package com.example.tradelog.api.validator;

import com.example.common.validator.FieldValidator;
import com.example.tradelog.api.spec.model.*;

import java.util.function.Predicate;

public class RequestValidation extends FieldValidator {

    public static Boolean validateGetAllTradedSymbols(String accountId) {
        return RequestValidation.accountId.test(accountId);
    }

    public static boolean validateGetSummary(String accountId) {
        return RequestValidation.accountId.test(accountId);
    }

    public static boolean validateGetAccountAndSymbol(String accountId, String symbol) {
        return !RequestValidation.accountId.test(accountId) || !RequestValidation.symbol.test(symbol);
    }


    public static boolean validateDeleteShareTrade(String accountId, String transactionId, String symbol) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.transactionId.test(transactionId)
                && RequestValidation.symbol.test(symbol);
    }

    public static boolean validateDeleteOptionTrade(String accountId, String transactionId, String symbol) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.transactionId.test(transactionId)
                && RequestValidation.symbol.test(symbol);
    }

    public static boolean validateGetDataBySymbol(String accountId, String symbol) {
        return !RequestValidation.accountId.test(accountId) || !RequestValidation.symbol.test(symbol);
    }

    public static boolean validateUpdateOptions(String accountId, String transactionId, TransactionOptionsModel model) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.transactionId.test(transactionId)
                && model != null;
    }

    static Predicate<ShareJournalModel> validateShareJournalModel = s -> s != null
            && s.getTransactionDetails() != null
            && s.getTransactionDetails().getType().equals(TransactionType.SHARE)
            && s.getTransactionDetails().getDate() != null
            && symbol.test(s.getTransactionDetails().getSymbol())
            && RequestValidation.accountId.test(s.getTransactionDetails().getAccountId())
            && s.getTransactionDetails().getId() == null
            && s.getAction() != null
            && s.getAction() != Action.UNKNOWN
            && s.getPrice() >= 0.00
            && s.getQuantity() >= 1;

    public static boolean validateCreateNewShareTrade(String accountId, String symbol, ShareJournalModel model) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.validateShareJournalModel.test(model)
                && model.getTransactionDetails().getSymbol().equals(symbol)
                && accountId.equals(model.getTransactionDetails().getAccountId());
    }

    public static boolean validateCreateNewOptionTrade(String accountId, String symbol, OptionJournalModel model) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.validateOptionJournalModel.test(model)
                && model.getTransactionDetails().getSymbol().equals(symbol)
                && accountId.equals(model.getTransactionDetails().getAccountId());
    }

    static Predicate<OptionJournalModel> validateOptionJournalModel = s -> s != null
            && s.getTransactionDetails() != null
            && s.getTransactionDetails().getType().equals(TransactionType.OPTION)
            && s.getTransactionDetails().getDate() != null
            && symbol.test(s.getTransactionDetails().getSymbol())
            && RequestValidation.accountId.test(s.getTransactionDetails().getAccountId())
            && s.getTransactionDetails().getId() == null
            && s.getAction() != null
            && s.getAction() != Action.UNKNOWN
            && (s.getOptionType() == OptionType.CALL || s.getOptionType() == OptionType.PUT)
            && s.getContracts() > 0
            && s.getPremium() >= 0
            && s.getExpiryDate() != null
            && s.getStockPrice() >= 0.00
            && s.getStrikePrice() > 0;
}
