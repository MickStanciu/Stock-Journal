package com.example.tradelog.api.validator;

import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import com.example.tradelog.api.spec.model.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class ValidateDividendModelTest {

    @Test
    void testValidateDividendJournalModelWhenNull() {
        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(null));
    }

    @Test
    void testValidateDividendJournalModelWhenDividendIsNegative() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.DIVIDEND, transactionSettingsModel);
        DividendJournalModel model = new DividendJournalModel(transactionModel, -1, 10);

        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelWhenQuantityIsNegative() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.DIVIDEND, transactionSettingsModel);
        DividendJournalModel model = new DividendJournalModel(transactionModel, 1, -1);

        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelWhenTransactionTypeIsNotDividend() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.UNKNOWN, transactionSettingsModel);
        DividendJournalModel model = new DividendJournalModel(transactionModel, 1, 1);

        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelTruthy() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123456789012345678901234567890123456", OffsetDateTime.now(), "XYZ", TransactionType.DIVIDEND, transactionSettingsModel);
        DividendJournalModel model = new DividendJournalModel(transactionModel, 10, 100);

        Assertions.assertTrue(RequestValidation.validateDividendJournalModel.test(model));
    }
}
