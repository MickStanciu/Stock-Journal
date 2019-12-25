//package com.example.tradelog.api.validator;
//
//import com.example.tradelog.api.core.model.ActionType;
//import com.example.tradelog.api.core.model.OptionJournalModel;
//import com.example.tradelog.api.core.model.OptionType;
//import com.example.tradelog.api.core.model.TransactionModel;
//import com.example.tradelog.api.core.model.TransactionSettingsModel;
//import com.example.tradelog.api.core.model.TransactionType;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.time.OffsetDateTime;
//
//class ValidateOptionJournalModelTest {
//
//    @Test
//    void testValidateOptionJournalModelWhenNull() {
//        Assertions.assertFalse(RequestValidation.validateShareJournalModel.test(null));
//    }
//
//    @Test
//    void testValidateOptionJournalModelWhenActionIsInvalid() {
//        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
//        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, 6.1f, transactionSettingsModel);
//        OptionJournalModel model = new OptionJournalModel(transactionModel, 1.1f, 2.1f, OffsetDateTime.now(), 1, 5.1f, ActionType.UNKNOWN, OptionType.PUT);
//
//        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
//    }
//
//    @Test
//    void testValidateOptionJournalModelWhenActionTypeIsUnknown() {
//        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
//        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, 6.1f, transactionSettingsModel);
//        OptionJournalModel model = new OptionJournalModel(transactionModel, 1.1f, 2.1f, OffsetDateTime.now(), 1, 5.1f, ActionType.BUY, OptionType.UNKNOWN);
//        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
//    }
//
//    @Test
//    void testValidateOptionJournalModelWhenStockPriceIsNegative() {
//        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
//        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, 6.1f, transactionSettingsModel);
//        OptionJournalModel model = new OptionJournalModel(transactionModel, -1, 2.1f, OffsetDateTime.now(), 1, 5.1f, ActionType.BUY, OptionType.PUT);
//
//        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
//    }
//
//    @Test
//    void testValidateOptionJournalModelWhenContractsIsZero() {
//        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
//        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, 6.1f, transactionSettingsModel);
//        OptionJournalModel model = new OptionJournalModel(transactionModel, 44.55, 2.1f, OffsetDateTime.now(), 0, 5.1f, ActionType.BUY, OptionType.PUT);
//
//        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
//    }
//
//    @Test
//    void testValidateOptionJournalModelWhenSymbolIsEmpty() {
//        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
//        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), "", TransactionType.OPTION, 6.1f, transactionSettingsModel);
//        OptionJournalModel model = new OptionJournalModel(transactionModel, 44.55, 2.1f, OffsetDateTime.now(), 1, 5.1f, ActionType.BUY, OptionType.PUT);
//
//        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
//    }
//
//    @Test
//    void testValidateOptionJournalModelWhenSymbolIsSpace() {
//        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
//        TransactionModel transactionModel = new TransactionModel("1234", "", OffsetDateTime.now(), " ", TransactionType.OPTION, 6.1f, transactionSettingsModel);
//        OptionJournalModel model = new OptionJournalModel(transactionModel, 44.55, 2.1f, OffsetDateTime.now(), 1, 5.1f, ActionType.BUY, OptionType.PUT);
//
//        Assertions.assertFalse(RequestValidation.validateOptionJournalModel.test(model));
//    }
//
//    @Test
//    void testValidateOptionJournalModelTruthy() {
//        TransactionSettingsModel transactionSettingsModel = new TransactionSettingsModel("1234", 0.00, false, false);
//        TransactionModel transactionModel = new TransactionModel("1234", "123456789012345678901234567890123456", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, 0, transactionSettingsModel);
//        OptionJournalModel model = new OptionJournalModel(transactionModel, 44.55, 55, OffsetDateTime.now(), 1, 1.5, ActionType.BUY, OptionType.CALL);
//
//        Assertions.assertTrue(RequestValidation.validateOptionJournalModel.test(model));
//    }
//}
