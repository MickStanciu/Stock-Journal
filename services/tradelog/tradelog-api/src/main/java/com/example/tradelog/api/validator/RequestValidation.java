package com.example.tradelog.api.validator;

import com.example.common.validator.FieldValidator;
import com.example.tradelog.api.core.model.ActionType;
import com.example.tradelog.api.core.model.DividendJournalModel;
import com.example.tradelog.api.core.model.OptionJournalModel;
import com.example.tradelog.api.core.model.OptionType;
import com.example.tradelog.api.core.model.ShareDataModel;
import com.example.tradelog.api.core.model.ShareJournalModel;
import com.example.tradelog.api.core.model.TransactionSettingsModel;
import com.example.tradelog.api.core.model.TransactionType;

import java.util.List;
import java.util.function.Predicate;

public class RequestValidation extends FieldValidator {

    public static Boolean validateGetAllTradedSymbols(String accountId) {
        return RequestValidation.accountId.test(accountId);
    }

    public static boolean validateGetSummary(String accountId) {
        return RequestValidation.accountId.test(accountId);
    }

    public static boolean validateGetAccountAndSymbol(String accountId, String symbol) {
        return RequestValidation.accountId.test(accountId) && RequestValidation.symbol.test(symbol);
    }


    public static boolean validateDeleteShareTrade(String accountId, String transactionId) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.transactionId.test(transactionId);
    }

    public static boolean validateEditShareTrade(String accountId, String transactionId, ShareJournalModel model) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.transactionId.test(transactionId)
                && RequestValidation.validateShareJournalModel.test(model)
                && accountId.equals(model.getTransactionDetails().getAccountId());
    }

    public static boolean validateDeleteOptionTrade(String accountId, String transactionId) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.transactionId.test(transactionId);
    }

    public static boolean validateGetDataBySymbol(String accountId, String symbol) {
        return RequestValidation.accountId.test(accountId) && RequestValidation.symbol.test(symbol);
    }

    public static boolean validateSetDataBySymbol(String accountId, String symbol, ShareDataModel model) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.symbol.test(symbol)
                && model.getSymbol().equals(symbol)
                && validateShareDataModel.test(model);
    }

    public static boolean validateUpdateOptions(String accountId, String transactionId, TransactionSettingsModel model) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.transactionId.test(transactionId)
                && model != null;
    }

    public static boolean validateUpdateSettingsBulk(String accountId, List<TransactionSettingsModel> models) {
        boolean modelsValid = models != null && models.stream()
                .map(TransactionSettingsModel::getTransactionId)
                .anyMatch(f -> RequestValidation.transactionId.test(f));

        return RequestValidation.accountId.test(accountId) && modelsValid;
    }

    public static boolean validateCreateNewShareTrade(String accountId, ShareJournalModel model) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.validateShareJournalModel.test(model)
                && accountId.equals(model.getTransactionDetails().getAccountId());
    }

    public static boolean validateCreateNewOptionTrade(String accountId, OptionJournalModel model) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.validateOptionJournalModel.test(model)
                && accountId.equals(model.getTransactionDetails().getAccountId());
    }

    public static boolean validateCreateNewDividendRecord(String accountId, DividendJournalModel model) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.validateDividendJournalModel.test(model)
                && accountId.equals(model.getTransactionDetails().getAccountId());
    }

    public static boolean validateDeleteDividendRecord(String accountId, String transactionId) {
        return RequestValidation.accountId.test(accountId)
                && RequestValidation.transactionId.test(transactionId);
    }

    static Predicate<ShareJournalModel> validateShareJournalModel = s -> s != null
            && s.getTransactionDetails() != null
            && s.getTransactionDetails().getType().equals(TransactionType.SHARE)
            && s.getTransactionDetails().getDate() != null
            && symbol.test(s.getTransactionDetails().getSymbol())
            && RequestValidation.accountId.test(s.getTransactionDetails().getAccountId())
            && s.getAction() != null
            && s.getAction() != ActionType.UNKNOWN
            && s.getPrice() >= 0.00
            && s.getQuantity() >= 1;

    static Predicate<OptionJournalModel> validateOptionJournalModel = s -> s != null
            && s.getTransactionDetails() != null
            && s.getTransactionDetails().getType().equals(TransactionType.OPTION)
            && s.getTransactionDetails().getDate() != null
            && symbol.test(s.getTransactionDetails().getSymbol())
            && RequestValidation.accountId.test(s.getTransactionDetails().getAccountId())
            && s.getAction() != null
            && s.getAction() != ActionType.UNKNOWN
            && (s.getOptionType() == OptionType.CALL || s.getOptionType() == OptionType.PUT)
            && s.getContracts() > 0
            && s.getPremium() >= 0
            && s.getExpiryDate() != null
            && s.getStockPrice() >= 0.00
            && s.getStrikePrice() > 0;

    static Predicate<DividendJournalModel> validateDividendJournalModel = s-> s != null
            && s.getTransactionDetails() != null
            && s.getTransactionDetails().getType().equals(TransactionType.DIVIDEND)
            && s.getTransactionDetails().getDate() != null
            && symbol.test(s.getTransactionDetails().getSymbol())
            && RequestValidation.accountId.test(s.getTransactionDetails().getAccountId())
            && s.getQuantity() >= 0
            && s.getDividend() >= 0;

    static Predicate<ShareDataModel> validateShareDataModel = s -> s != null
            && s.getSymbol() != null;

}
