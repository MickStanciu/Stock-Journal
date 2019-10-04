package com.example.tradelog.api.validator;

import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import com.example.tradelog.api.spec.model.TransactionType;
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
    void testValidateShareJournalModelWhenActionIsNull() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, transactionSettingsModel);

        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModel)
                .withQuantity(1)
                .withAction(null)
                .withPrice(1)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenActionIsInvalid() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, transactionSettingsModel);

        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModel)
                .withQuantity(1)
                .withAction(Action.UNKNOWN)
                .withPrice(1)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenAccountIsEmtpy() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, transactionSettingsModel);

        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModel)
                .withQuantity(1)
                .withAction(Action.BUY)
                .withPrice(1)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenPriceIsNegative() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, transactionSettingsModel);

        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModel)
                .withPrice(-1)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenQuantityIsZero() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, transactionSettingsModel);

        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModel)
                .withQuantity(0)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenSymbolIsEmpty() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "", TransactionType.SHARE, transactionSettingsModel);

        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModel)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenSymbolIsSpace() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), " ", TransactionType.SHARE, transactionSettingsModel);

        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModel)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelWhenTransactionTypeIsNotStock() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.UNKNOWN, transactionSettingsModel);

        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModel)
                .build();

        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(model));
    }

    @Test
    void testValidateShareJournalModelTruthy() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123456789012345678901234567890123456", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, transactionSettingsModel);

        ShareJournalModel model = shareBuilder
                .withTransactionModel(transactionModel)
                .withAction(Action.BUY)
                .withBrokerFees(0)
                .withPrice(10.00)
                .withQuantity(100)
                .build();

        Assertions.assertTrue(RequestValidation.validateShareJournalModel.test(model));
    }
}
