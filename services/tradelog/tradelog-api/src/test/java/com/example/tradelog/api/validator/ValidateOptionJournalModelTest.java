package com.example.tradelog.api.validator;

import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.ActionType;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class ValidateOptionJournalModelTest {

    private OptionJournalModel.Builder optionBuilder;

    ValidateOptionJournalModelTest() {
        optionBuilder = new OptionJournalModel.Builder();
    }

    @Test
    void testValidateOptionJournalModelWhenNull() {
        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(null));
    }

    @Test
    void testValidateOptionJournalModelWhenDefault() {
        OptionJournalModel model = optionBuilder.build();
        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenAccountIdIsNull() {
        OptionJournalModel model = optionBuilder
                .withAccountId(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionIsNull() {
        OptionJournalModel model = optionBuilder
                .withAction(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionIsInvalid() {
        OptionJournalModel model = optionBuilder
                .withAction(Action.UNKNOWN)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionTypeIsNull() {
        OptionJournalModel model = optionBuilder
                .withActionType(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionTypeIsUnknown() {
        OptionJournalModel model = optionBuilder
                .withActionType(ActionType.UNKNOWN)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionTypeIsStock() {
        OptionJournalModel model = optionBuilder
                .withActionType(ActionType.STOCK)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenDateIsNull() {
        OptionJournalModel model = optionBuilder
                .withDate(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenStockPriceIsNegative() {
        OptionJournalModel model = optionBuilder
                .withStockPrice(-1)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenContractsIsZero() {
        OptionJournalModel model = optionBuilder
                .withContracts(0)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenSymbolIsNull() {
        OptionJournalModel model = optionBuilder
                .withStockSymbol(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenSymbolIsEmpty() {
        OptionJournalModel model = optionBuilder
                .withStockSymbol("")
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenSymbolIsSpace() {
        OptionJournalModel model = optionBuilder
                .withStockSymbol("     ")
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelTruthy() {
        OptionJournalModel model = optionBuilder
                .withStockSymbol("XYZ")
                .withAction(Action.BUY)
                .withActionType(ActionType.CALL)
                .withAccountId("123456789012345678901234567890123456")
                .withBrokerFees(0)
                .withDate(OffsetDateTime.now())
                .withExpiryDate(OffsetDateTime.now())
                .withMark("1")
                .withStockPrice(10.00)
                .withStrikePrice(10.00)
                .withContracts(1)
                .withImpliedVolatility(0)
                .withHistoricalImpliedVolatility(0)
                .withProfitProbability(0)
                .withPremium(1.50)
                .build();

        Assertions.assertTrue(RequestValidation.validateOptionJournalModel.test(model));
    }
}
