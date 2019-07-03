package com.example.tradelog.api.validator;

import com.example.tradelog.api.spec.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class ValidateOptionJournalModelTest {

    private OptionJournalModel.Builder optionBuilder;

    private TransactionModel.Builder transactionModelBuilder = TransactionModel.builder()
            .withAccountId("123456789012345678901234567890123456")
            .withType(TransactionType.OPTION)
            .withDate(OffsetDateTime.now())
            .withSymbol("XYZ");

    ValidateOptionJournalModelTest() {
        optionBuilder = new OptionJournalModel.Builder();
    }

    @Test
    void testValidateOptionJournalModelWhenNull() {
        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(null));
    }

    @Test
    void testValidateOptionJournalModelWhenDefault() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .build();
        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenAccountIdIsNull() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.withAccountId(null).build())
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionIsNull() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .withAction(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionIsInvalid() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .withAction(Action.UNKNOWN)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionTypeIsNull() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .withActionType(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionTypeIsUnknown() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .withActionType(OptionType.UNKNOWN)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionTypeIsInvalid() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .withActionType(OptionType.UNKNOWN)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenDateIsNull() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.withDate(null).build())
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenStockPriceIsNegative() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .withStockPrice(-1)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenContractsIsZero() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .withContracts(0)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenSymbolIsNull() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.withSymbol(null).build())
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenSymbolIsEmpty() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.withSymbol("").build())
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenSymbolIsSpace() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.withSymbol(" ").build())
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelTruthy() {
        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModelBuilder.build())
                .withAction(Action.BUY)
                .withActionType(OptionType.CALL)
                .withBrokerFees(0)
                .withExpiryDate(OffsetDateTime.now())
                .withStockPrice(10.00)
                .withStrikePrice(10.00)
                .withContracts(1)
                .withPremium(1.50)
                .build();

        Assertions.assertTrue(RequestValidation.validateOptionJournalModel.test(model));
    }
}
