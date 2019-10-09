package com.example.tradelog.api.validator;

import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.OptionType;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import com.example.tradelog.api.spec.model.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class ValidateOptionJournalModelTest {

    @Test
    void testValidateOptionJournalModelWhenNull() {
        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(null));
    }

    @Test
    void testValidateOptionJournalModelWhenActionIsInvalid() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, transactionSettingsModel);
        OptionJournalModel model = new OptionJournalModel(transactionModel, 1.1f, 2.1f, OffsetDateTime.now(), 1, 5.1f, Action.UNKNOWN, OptionType.PUT, 6.1f);

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionTypeIsUnknown() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, transactionSettingsModel);
        OptionJournalModel model = new OptionJournalModel(transactionModel, 1.1f, 2.1f, OffsetDateTime.now(), 1, 5.1f, Action.BUY, OptionType.UNKNOWN, 6.1f);
        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenStockPriceIsNegative() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, transactionSettingsModel);
        OptionJournalModel model = new OptionJournalModel(transactionModel, -1, 2.1f, OffsetDateTime.now(), 1, 5.1f, Action.BUY, OptionType.PUT, 6.1f);

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenContractsIsZero() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, transactionSettingsModel);
        OptionJournalModel model = new OptionJournalModel(transactionModel, 44.55, 2.1f, OffsetDateTime.now(), 0, 5.1f, Action.BUY, OptionType.PUT, 6.1f);

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenSymbolIsEmpty() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "", TransactionType.OPTION, transactionSettingsModel);
        OptionJournalModel model = new OptionJournalModel(transactionModel, 44.55, 2.1f, OffsetDateTime.now(), 1, 5.1f, Action.BUY, OptionType.PUT, 6.1f);

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenSymbolIsSpace() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), " ", TransactionType.OPTION, transactionSettingsModel);
        OptionJournalModel model = new OptionJournalModel(transactionModel, 44.55, 2.1f, OffsetDateTime.now(), 1, 5.1f, Action.BUY, OptionType.PUT, 6.1f);

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelTruthy() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123456789012345678901234567890123456", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, transactionSettingsModel);
        OptionJournalModel model = new OptionJournalModel(transactionModel, 44.55, 55, OffsetDateTime.now(), 1, 1.5, Action.BUY, OptionType.CALL, 0);

        Assertions.assertTrue(RequestValidation.validateOptionJournalModel.test(model));
    }
}
