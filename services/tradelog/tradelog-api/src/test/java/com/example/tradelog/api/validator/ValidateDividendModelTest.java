package com.example.tradelog.api.validator;

import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class ValidateDividendModelTest {

    private DividendJournalModel.Builder dividendBuilder;

    private TransactionModel.Builder transactionModelBuilder = TransactionModel.builder()
            .withAccountId("123456789012345678901234567890123456")
            .withType(TransactionType.DIVIDEND)
            .withDate(OffsetDateTime.now())
            .withSymbol("XYZ");

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
    void testValidateDividendJournalModelWhenAccountIdIsNull() {
        DividendJournalModel model = dividendBuilder
                .withTransactionDetails(transactionModelBuilder.withAccountId(null).build())
                .withQuantity(1)
                .withDividend(1)
                .build();

        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelWhenDateIsNull() {
        DividendJournalModel model = dividendBuilder
                .withTransactionDetails(transactionModelBuilder.withDate(null).build())
                .build();

        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelWhenDividendIsNegative() {
        DividendJournalModel model = dividendBuilder
                .withTransactionDetails(transactionModelBuilder.build())
                .withDividend(-1)
                .withQuantity(10)
                .build();

        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelWhenQuantityIsNegative() {
        DividendJournalModel model = dividendBuilder
                .withTransactionDetails(transactionModelBuilder.build())
                .withDividend(1)
                .withQuantity(-1)
                .build();

        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelWhenSymbolIsNull() {
        DividendJournalModel model = dividendBuilder
                .withTransactionDetails(transactionModelBuilder.withSymbol(null).build())
                .build();

        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelWhenTransactionTypeIsNotDividend() {
        DividendJournalModel model = dividendBuilder
                .withTransactionDetails(transactionModelBuilder.withType(TransactionType.UNKNOWN).build())
                .build();

        Assertions.assertFalse(RequestValidation.validateDividendJournalModel.test(model));
    }

    @Test
    void testValidateDividendJournalModelTruthy() {
        DividendJournalModel model = dividendBuilder
                .withTransactionDetails(transactionModelBuilder.build())
                .withDividend(10.00)
                .withQuantity(100)
                .build();

        Assertions.assertTrue(RequestValidation.validateDividendJournalModel.test(model));
    }
}
