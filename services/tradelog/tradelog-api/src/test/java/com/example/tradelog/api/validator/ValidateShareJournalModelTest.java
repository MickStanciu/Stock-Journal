package com.example.tradelog.api.validator;

import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.ActionType;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class ValidateShareJournalModelTest {

    private ShareJournalModel.Builder shareBuilder;

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
                .withAccountId(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenActionIsNull() {
        ShareJournalModel model = shareBuilder
                .withAction(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenActionIsInvalid() {
        ShareJournalModel model = shareBuilder
                .withAction(Action.UNKNOWN)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenActionTypeIsNull() {
        ShareJournalModel model = shareBuilder
                .withActionType(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenActionTypeIsUnknown() {
        ShareJournalModel model = shareBuilder
                .withActionType(ActionType.UNKNOWN)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenDateIsNull() {
        ShareJournalModel model = shareBuilder
                .withDate(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenPriceIsNegative() {
        ShareJournalModel model = shareBuilder
                .withPrice(-1)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenQuantityIsZero() {
        ShareJournalModel model = shareBuilder
                .withQuantity(0)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenSymbolIsNull() {
        ShareJournalModel model = shareBuilder
                .withSymbol(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenSymbolIsEmpty() {
        ShareJournalModel model = shareBuilder
                .withSymbol("")
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenSymbolIsSpace() {
        ShareJournalModel model = shareBuilder
                .withSymbol("     ")
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelTruthy() {
        ShareJournalModel model = shareBuilder
                .withSymbol("XYZ")
                .withAction(Action.BUY)
                .withActionType(ActionType.STOCK)
                .withAccountId("123456789012345678901234567890123456")
                .withBrokerFees(0)
                .withDate(OffsetDateTime.now())
                .withMark("1")
                .withPrice(10.00)
                .withQuantity(100)
                .build();

        Assertions.assertTrue(RequestValidation.validateShareJournalModel.test(model));
    }
}
