package com.example.gateway.api.converter;

import com.example.gateway.api.spec.model.OptionJournalGWModel;
import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.OptionType;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import com.example.tradelog.api.spec.model.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class OptionJournalConverterTest {

    @Test
    void testConverter() {
        TransactionSettingsModel settingsModel = new TransactionSettingsModel("1234", 44.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.OPTION, 6.1f, settingsModel);
        OptionJournalModel model = new OptionJournalModel(transactionModel, 1.1f, 2.1f, OffsetDateTime.now(), 1, 5.1f, Action.SELL, OptionType.PUT);

        OptionJournalGWModel gwModel = OptionJournalConverter.toOptionGWModel.apply(model);

        Assertions.assertEquals(model.getTransactionDetails().getId(), gwModel.getTransactionId());
        Assertions.assertEquals(model.getTransactionDetails().getAccountId(), gwModel.getAccountId());
        Assertions.assertEquals(model.getTransactionDetails().getDate(), gwModel.getDate());
        Assertions.assertEquals(model.getTransactionDetails().getSymbol(), gwModel.getStockSymbol());
        Assertions.assertEquals(model.getTransactionDetails().getBrokerFees(), gwModel.getBrokerFees());
        Assertions.assertEquals(model.getAction().name(), gwModel.getAction().name());
        Assertions.assertEquals(model.getTransactionDetails().getType().name(), gwModel.getType().name());
        Assertions.assertEquals(model.getOptionType().name(), gwModel.getOptionType().name());
        Assertions.assertEquals(model.getStockPrice(), gwModel.getStockPrice());
        Assertions.assertEquals(model.getStrikePrice(), gwModel.getStrikePrice());
        Assertions.assertEquals(model.getExpiryDate(), gwModel.getExpiryDate());
        Assertions.assertEquals(model.getContracts(), gwModel.getContracts());
        Assertions.assertEquals(model.getPremium(), gwModel.getPremium());
    }
}
