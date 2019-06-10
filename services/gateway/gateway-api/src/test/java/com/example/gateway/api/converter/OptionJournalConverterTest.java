package com.example.gateway.api.converter;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.ActionType;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class OptionJournalConverterTest {

    @Test
    void testConverter() {
        OptionJournalModel model = OptionJournalModel.builder()
                .withAccountId("123")
                .withAction(Action.SELL)
                .withActionType(ActionType.PUT)
                .withTransactionId("1234")
                .withPairTransactionId("2345")
                .withDate(OffsetDateTime.now())
                .withStockSymbol("XYZ")
                .withStockPrice(1.1f)
                .withStrikePrice(2.1f)
                .withExpiryDate(OffsetDateTime.now())
                .withImpliedVolatility(3.1f)
                .withProfitProbability(4.1f)
                .withContracts(1)
                .withPremium(5.1f)
                .withBrokerFees(6.1f)
                .withMark("ABC")
                .build();


        OptionJournalGWModel gwModel = new OptionJournalConverter().apply(model);

        Assertions.assertEquals(model.getAccountId(), gwModel.getAccountId());
        Assertions.assertEquals(model.getAction().name(), gwModel.getAction().name());
        Assertions.assertEquals(model.getActionType().name(), gwModel.getActionType().name());
        Assertions.assertEquals(model.getTransactionId(), gwModel.getTransactionId());
        Assertions.assertEquals(model.getDate(), gwModel.getDate());
        Assertions.assertEquals(model.getStockSymbol(), gwModel.getStockSymbol());
        Assertions.assertEquals(model.getStockPrice(), gwModel.getStockPrice());
        Assertions.assertEquals(model.getStrikePrice(), gwModel.getStrikePrice());
        Assertions.assertEquals(model.getExpiryDate(), gwModel.getExpiryDate());
        Assertions.assertEquals(model.getImpliedVolatility(), gwModel.getImpliedVolatility());
        Assertions.assertEquals(model.getContracts(), gwModel.getContracts());
        Assertions.assertEquals(model.getPremium(), gwModel.getPremium());
        Assertions.assertEquals(model.getBrokerFees(), gwModel.getBrokerFees());
        Assertions.assertEquals(model.getMark(), gwModel.getMark());
    }
}
