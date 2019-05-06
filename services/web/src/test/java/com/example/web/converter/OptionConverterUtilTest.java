package com.example.web.converter;

import com.example.gateway.api.model.ActionGW;
import com.example.gateway.api.model.ActionTypeGW;
import com.example.gateway.api.model.OptionJournalGWModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

class OptionConverterUtilTest {

    private OptionConverterUtil converter;
    private OffsetDateTime expDate;

    OptionConverterUtilTest() {
        this.converter = new OptionConverterUtil();
        this.expDate = OffsetDateTime.of(
                LocalDateTime.of(2019, 5, 12, 0, 0),
                ZoneOffset.UTC);
    }

    @Test
    void testGeneratorShortPUT() {
        OptionJournalGWModel model = OptionJournalGWModel.builder()
                .withStockSymbol("CSCO")
                .withContracts(1)
                .withDate(this.expDate.minusMonths(1))
                .withPremium(0.5)
                .withStrikePrice(77.5)
                .withExpiryDate(this.expDate)
                .withAction(ActionGW.SELL)
                .withActionType(ActionTypeGW.PUT)
                .build();

        String expected = "SOLD 1 CSCO May12'19 77.5 PUT @ 0.5";
        String actual = converter.generateName.apply(model);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGeneratorOppositeNameShortPUT() {
        OptionJournalGWModel model = OptionJournalGWModel.builder()
                .withStockSymbol("CSCO")
                .withContracts(1)
                .withDate(this.expDate.minusMonths(1))
                .withPremium(0.5)
                .withStrikePrice(77.5)
                .withExpiryDate(this.expDate)
                .withAction(ActionGW.SELL)
                .withActionType(ActionTypeGW.PUT)
                .build();

        String expected = "BOUGHT 1 CSCO May12'19 77.5 PUT @ 0.5";
        String actual = converter.generateOppositeActionName.apply(model);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGeneratorLongPUT() {
        OptionJournalGWModel model = OptionJournalGWModel.builder()
                .withStockSymbol("CSCO")
                .withContracts(1)
                .withDate(this.expDate.minusMonths(1))
                .withPremium(0.5)
                .withStrikePrice(77.5)
                .withExpiryDate(this.expDate)
                .withAction(ActionGW.BUY)
                .withActionType(ActionTypeGW.PUT)
                .build();

        String expected = "BOUGHT 1 CSCO May12'19 77.5 PUT @ 0.5";
        String actual = converter.generateName.apply(model);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGeneratorShortCALL() {
        OptionJournalGWModel model = OptionJournalGWModel.builder()
                .withStockSymbol("CSCO")
                .withContracts(1)
                .withDate(this.expDate.minusMonths(1))
                .withPremium(0.5)
                .withStrikePrice(77.5)
                .withExpiryDate(this.expDate)
                .withAction(ActionGW.SELL)
                .withActionType(ActionTypeGW.CALL)
                .build();

        String expected = "SOLD 1 CSCO May12'19 77.5 CALL @ 0.5";
        String actual = converter.generateName.apply(model);
        Assertions.assertEquals(expected, actual);
    }
}
