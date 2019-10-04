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

    private OptionJournalModel.Builder optionBuilder;

    ValidateOptionJournalModelTest() {
        optionBuilder = new OptionJournalModel.Builder();
    }

    @Test
    void testValidateOptionJournalModelWhenNull() {
        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(null));
    }

    @Test
    void testValidateOptionJournalModelWhenActionIsNull() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, transactionSettingsModel);

        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModel)
                .withAction(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionIsInvalid() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, transactionSettingsModel);

        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModel)
                .withAction(Action.UNKNOWN)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionTypeIsNull() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, transactionSettingsModel);

        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModel)
                .withAction(Action.BUY)
                .withActionType(null)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenActionTypeIsUnknown() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, transactionSettingsModel);

        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModel)
                .withAction(Action.BUY)
                .withActionType(OptionType.UNKNOWN)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenStockPriceIsNegative() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, transactionSettingsModel);

        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModel)
                .withAction(Action.BUY)
                .withActionType(OptionType.CALL)
                .withStockPrice(-1)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenContractsIsZero() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, transactionSettingsModel);

        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModel)
                .withContracts(0)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenSymbolIsEmpty() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "", TransactionType.OPTION, transactionSettingsModel);

        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModel)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelWhenSymbolIsSpace() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), " ", TransactionType.OPTION, transactionSettingsModel);

        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModel)
                .build();

        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
    }

    @Test
    void testValidateOptionJournalModelTruthy() {
        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123456789012345678901234567890123456", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, transactionSettingsModel);

        OptionJournalModel model = optionBuilder
                .withTransactionModel(transactionModel)
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
