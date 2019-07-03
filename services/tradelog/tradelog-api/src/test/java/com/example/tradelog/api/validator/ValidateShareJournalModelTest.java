package com.example.tradelog.api.validator;

import com.example.tradelog.api.spec.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class ValidateShareJournalModelTest {

    private ShareJournalModel.Builder shareBuilder;

    private TransactionModel.Builder transactionModelBuilder = TransactionModel.builder()
            .withAccountId("123456789012345678901234567890123456")
            .withType(TransactionType.SHARE)
            .withDate(OffsetDateTime.now())
            .withSymbol("XYZ");

    ValidateShareJournalModelTest() {
        shareBuilder = new ShareJournalModel.Builder();
    }

    @Test
    void testValidateShareJournalModelWhenNull() {
        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(null));
    }

    @Test
    void testValidateShareJournalModelWhenDefault() {
        ShareJournalModel model = shareBuilder.build();
        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenAccountIdIsNull() {
        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModelBuilder.withAccountId(null).build())
                .withQuantity(1)
                .withAction(Action.BUY)
                .withPrice(1)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenActionIsNull() {
        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .withQuantity(1)
                .withAction(null)
                .withPrice(1)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenActionIsInvalid() {
        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModelBuilder.withAccountId(null).build())
                .withQuantity(1)
                .withAction(Action.UNKNOWN)
                .withPrice(1)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }


    @Test
    void testValidateShareJournalModelWhenActionTypeIsUnknown() {
        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModelBuilder.withAccountId(null).build())
                .withQuantity(1)
                .withAction(Action.BUY)
                .withPrice(1)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenDateIsNull() {
        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModelBuilder.withDate(null).build())
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenPriceIsNegative() {
        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .withPrice(-1)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenQuantityIsZero() {
        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .withQuantity(0)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenSymbolIsNull() {
        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModelBuilder.withSymbol(null).build())
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenSymbolIsEmpty() {
        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModelBuilder.withSymbol("").build())
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenSymbolIsSpace() {
        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModelBuilder.withSymbol(" ").build())
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenTransactionTypeIsNotStock() {
        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModelBuilder.withType(TransactionType.UNKNOWN).build())
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelTruthy() {
        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .withAction(Action.BUY)
                .withBrokerFees(0)
                .withPrice(10.00)
                .withQuantity(100)
                .build();

        Assertions.assertTrue(RequestValidation.validateShareJournalModel.test(model));
    }
}
