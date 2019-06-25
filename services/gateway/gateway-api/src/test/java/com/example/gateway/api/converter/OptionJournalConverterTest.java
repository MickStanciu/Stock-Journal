package com.example.gateway.api.converter;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.ActionType;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class OptionJournalConverterTest {

    @Test
    void testConverter() {
        TransactionModel transactionModel = TransactionModel.builder()
                .withId("1234")
                .withAccountId("123")
                .withDate(OffsetDateTime.now())
                .withSymbol("XYZ")
                .build();

        OptionJournalModel model = OptionJournalModel.builder()
                .withTransactionModel(transactionModel)
                .withAction(Action.SELL)
                .withActionType(ActionType.PUT)
                .withStockPrice(1.1f)
                .withStrikePrice(2.1f)
                .withExpiryDate(OffsetDateTime.now())
                .withContracts(1)
                .withPremium(5.1f)
                .withBrokerFees(6.1f)
                .build();


        OptionJournalGWModel gwModel = OptionJournalConverter.toOptionGWModel.apply(model);

        Assertions.assertEquals(model.getTransactionDetails().getId(), gwModel.getTransactionId());
        Assertions.assertEquals(model.getTransactionDetails().getAccountId(), gwModel.getAccountId());
        Assertions.assertEquals(model.getTransactionDetails().getDate(), gwModel.getDate());
        Assertions.assertEquals(model.getTransactionDetails().getSymbol(), gwModel.getStockSymbol());
        Assertions.assertEquals(model.getAction().name(), gwModel.getAction().name());
        Assertions.assertEquals(model.getActionType().name(), gwModel.getActionType().name());
        Assertions.assertEquals(model.getStockPrice(), gwModel.getStockPrice());
        Assertions.assertEquals(model.getStrikePrice(), gwModel.getStrikePrice());
        Assertions.assertEquals(model.getExpiryDate(), gwModel.getExpiryDate());
        Assertions.assertEquals(model.getContracts(), gwModel.getContracts());
        Assertions.assertEquals(model.getPremium(), gwModel.getPremium());
        Assertions.assertEquals(model.getBrokerFees(), gwModel.getBrokerFees());
    }
}
