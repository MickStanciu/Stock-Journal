package com.example.tradelog.api.validator;

import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import com.example.tradelog.api.spec.model.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class ValidateDividendModelTest {

    private DividendJournalModel.Builder dividendBuilder;

    ValidateDividendModelTest() {
        dividendBuilder = new DividendJournalModel.Builder();
    }

    @Test
    void testValidateDividendJournalModelWhenNull() {
        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(null));
    }

    @Test
    void testValidateDividendJournalModelWhenDefault() {
        DividendJournalModel model = dividendBuilder.build();
        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelWhenDividendIsNegative() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.DIVIDEND, transactionSettingsModel);

        DividendJournalModel model = dividendBuilder
                .withTransactionDetails(transactionModel)
                .withDividend(-1)
                .withQuantity(10)
                .build();

        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelWhenQuantityIsNegative() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.DIVIDEND, transactionSettingsModel);

        DividendJournalModel model = dividendBuilder
                .withTransactionDetails(transactionModel)
                .withDividend(1)
                .withQuantity(-1)
                .build();

        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelWhenTransactionTypeIsNotDividend() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.UNKNOWN, transactionSettingsModel);

        DividendJournalModel model = dividendBuilder
                .withTransactionDetails(transactionModel)
                .build();

        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelTruthy() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123456789012345678901234567890123456", OffsetDateTime.now(), "XYZ", TransactionType.DIVIDEND, transactionSettingsModel);

        DividendJournalModel model = dividendBuilder
                .withTransactionDetails(transactionModel)
                .withDividend(10.00)
                .withQuantity(100)
                .build();

        Assertions.assertTrue(RequestValidation.validateDividendJournalModel.test(model));
    }
}
