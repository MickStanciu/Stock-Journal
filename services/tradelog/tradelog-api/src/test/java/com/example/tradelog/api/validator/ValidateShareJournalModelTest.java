package com.example.tradelog.api.validator;

import com.example.tradelog.api.spec.model.ActionType;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import com.example.tradelog.api.spec.model.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class ValidateShareJournalModelTest {

    @Test
    void testValidateShareJournalModelWhenNull() {
        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(null));
    }


    @Test
    void testValidateShareJournalModelWhenActionIsInvalid() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, 0, transactionSettingsModel);
        ShareJournalModel model = new ShareJournalModel(transactionModel, 1, 1, 1, ActionType.UNKNOWN);

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenAccountIsEmtpy() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, 0, transactionSettingsModel);
        ShareJournalModel model = new ShareJournalModel(transactionModel, 1, 1, 1, ActionType.BUY);

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenPriceIsNegative() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, 0, transactionSettingsModel);
        ShareJournalModel model = new ShareJournalModel(transactionModel, -1, 1, 1, ActionType.BUY);

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenQuantityIsZero() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, 0, transactionSettingsModel);
        ShareJournalModel model = new ShareJournalModel(transactionModel, 1, 1, 0, ActionType.BUY);

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenSymbolIsEmpty() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "", TransactionType.SHARE, 0, transactionSettingsModel);
        ShareJournalModel model = new ShareJournalModel(transactionModel, 1, 1, 1, ActionType.BUY);

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenSymbolIsSpace() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), " ", TransactionType.SHARE, 0, transactionSettingsModel);
        ShareJournalModel model = new ShareJournalModel(transactionModel, 1, 1, 1, ActionType.BUY);

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenTransactionTypeIsNotStock() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.UNKNOWN, 0, transactionSettingsModel);
        ShareJournalModel model = new ShareJournalModel(transactionModel, 1, 1, 1, ActionType.BUY);

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelTruthy() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123456789012345678901234567890123456", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, 0, transactionSettingsModel);
        ShareJournalModel model = new ShareJournalModel(transactionModel, 10.00, 1, 100, ActionType.BUY);

        Assertions.assertTrue(RequestValidation.validateShareJournalModel.test(model));
    }
}
