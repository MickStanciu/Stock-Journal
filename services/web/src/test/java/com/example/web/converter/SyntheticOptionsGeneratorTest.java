package com.example.web.converter;

import com.example.gateway.api.spec.model.ActionGW;
import com.example.gateway.api.spec.model.OptionTypeGW;
import com.example.gateway.api.spec.model.OptionJournalGWModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

class SyntheticOptionsGeneratorTest {

    private SyntheticOptionsGenerator generator;
    private List<OptionJournalGWModel> optionList;

    SyntheticOptionsGeneratorTest() {
        this.generator = new SyntheticOptionsGenerator();
    }

    @BeforeEach
    void init() {
        this.optionList = new ArrayList<>();
    }

    @Test
    void testWhereThereIsNoNeedToGenerate() {
        optionList.add(OptionJournalGWModel.builder()
                .withStockSymbol("CSCO")
                .withContracts(1)
                .withDate(OffsetDateTime.now())
                .withStrikePrice(55)
                .withPremium(0.5)
                .withExpiryDate(OffsetDateTime.now().plusMonths(1))
                .withAction(ActionGW.SELL)
                .withOptionType(OptionTypeGW.PUT)
                .build());

        optionList.add(OptionJournalGWModel.builder()
                .withStockSymbol("CSCO")
                .withContracts(1)
                .withDate(OffsetDateTime.now())
                .withStrikePrice(55)
                .withPremium(0.5)
                .withExpiryDate(OffsetDateTime.now().plusMonths(1))
                .withAction(ActionGW.BUY)
                .withOptionType(OptionTypeGW.PUT)
                .build());

        List<OptionJournalGWModel> syn = generator.apply(optionList);
        Assertions.assertEquals(0, syn.size());
    }

    @Test
    void testSynCSCO1() {
        OffsetDateTime exp = OffsetDateTime.now().plusMonths(1);

        optionList.add(OptionJournalGWModel.builder()
                .withStockSymbol("CSCO")
                .withContracts(1)
                .withDate(OffsetDateTime.now())
                .withStrikePrice(55)
                .withPremium(0.5)
                .withExpiryDate(exp)
                .withAction(ActionGW.SELL)
                .withOptionType(OptionTypeGW.PUT)
                .build());

        List<OptionJournalGWModel> syn = generator.apply(optionList);
        Assertions.assertEquals(1, syn.size());

        OptionJournalGWModel csco = syn.get(0);
        Assertions.assertEquals("CSCO", csco.getStockSymbol());
        Assertions.assertEquals(55, csco.getStrikePrice());
        Assertions.assertEquals(ActionGW.BUY, csco.getAction());
        Assertions.assertEquals(1, csco.getContracts());
        Assertions.assertEquals(0, csco.getPremium());
        Assertions.assertEquals(exp, csco.getExpiryDate());
        Assertions.assertEquals(exp, csco.getDate());
    }
}
