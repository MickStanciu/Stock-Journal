package com.example.web.converter;

import com.example.gateway.api.model.ActionGW;
import com.example.gateway.api.model.ActionTypeGW;
import com.example.gateway.api.model.ShareJournalGWModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

class SyntheticSharesGeneratorTest {

    private SyntheticSharesGenerator generator;
    private List<ShareJournalGWModel> shareList;

    SyntheticSharesGeneratorTest() {
        generator = new SyntheticSharesGenerator();
    }

    @BeforeEach
    void init() {
        shareList = new ArrayList<>();
    }

    @Test
    void testWhereThereIsNoNeedToGenerate() {
        shareList.add(ShareJournalGWModel.builder()
                .withSymbol("CSCO")
                .withQuantity(500)
                .withDate(OffsetDateTime.now())
                .withPrice(10.0)
                .withAction(ActionGW.BUY)
                .withActionType(ActionTypeGW.STOCK)
                .build());

        shareList.add(ShareJournalGWModel.builder()
                .withSymbol("CSCO")
                .withQuantity(500)
                .withDate(OffsetDateTime.now())
                .withPrice(10.0)
                .withAction(ActionGW.SELL)
                .withActionType(ActionTypeGW.STOCK)
                .build());

        List<ShareJournalGWModel> syn = generator.apply(shareList);
        Assertions.assertEquals(0, syn.size());
    }

    @Test
    void testSynCSCO100() {
        shareList.add(ShareJournalGWModel.builder()
                .withSymbol("CSCO")
                .withQuantity(500)
                .withDate(OffsetDateTime.now())
                .withPrice(10.0)
                .withAction(ActionGW.BUY)
                .withActionType(ActionTypeGW.STOCK)
                .build());

        shareList.add(ShareJournalGWModel.builder()
                .withSymbol("CSCO")
                .withQuantity(400)
                .withDate(OffsetDateTime.now())
                .withPrice(10.0)
                .withAction(ActionGW.SELL)
                .withActionType(ActionTypeGW.STOCK)
                .build());

        List<ShareJournalGWModel> syn = generator.apply(shareList);
        Assertions.assertEquals(1, syn.size());

        ShareJournalGWModel csco100 = syn.get(0);
        Assertions.assertEquals(ActionGW.SELL, csco100.getAction());
        Assertions.assertEquals(100, csco100.getQuantity());
        Assertions.assertEquals(10.0, csco100.getPrice());
    }

    @Test
    void testAveragePriceCSCO() {
        shareList.add(ShareJournalGWModel.builder()
                .withSymbol("CSCO")
                .withQuantity(100)
                .withDate(OffsetDateTime.now())
                .withPrice(55.0)
                .withAction(ActionGW.BUY)
                .withActionType(ActionTypeGW.STOCK)
                .build());

        shareList.add(ShareJournalGWModel.builder()
                .withSymbol("CSCO")
                .withQuantity(200)
                .withDate(OffsetDateTime.now())
                .withPrice(66.0)
                .withAction(ActionGW.BUY)
                .withActionType(ActionTypeGW.STOCK)
                .build());

        List<ShareJournalGWModel> syn = generator.apply(shareList);
        Assertions.assertEquals(1, syn.size());

        ShareJournalGWModel csco = syn.get(0);
        Assertions.assertEquals(ActionGW.SELL, csco.getAction());
        Assertions.assertEquals(300, csco.getQuantity());
        Assertions.assertEquals(62.333333333333336, csco.getPrice());
    }

    @Test
    void testSynCSCO100AndADBE200() {
        shareList.add(ShareJournalGWModel.builder()
                .withSymbol("CSCO")
                .withQuantity(500)
                .withDate(OffsetDateTime.now())
                .withPrice(10.0)
                .withAction(ActionGW.BUY)
                .withActionType(ActionTypeGW.STOCK)
                .build());

        shareList.add(ShareJournalGWModel.builder()
                .withSymbol("ADBE")
                .withQuantity(500)
                .withDate(OffsetDateTime.now())
                .withPrice(10.0)
                .withAction(ActionGW.BUY)
                .withActionType(ActionTypeGW.STOCK)
                .build());


        shareList.add(ShareJournalGWModel.builder()
                .withSymbol("CSCO")
                .withQuantity(400)
                .withDate(OffsetDateTime.now())
                .withPrice(10.0)
                .withAction(ActionGW.SELL)
                .withActionType(ActionTypeGW.STOCK)
                .build());

        shareList.add(ShareJournalGWModel.builder()
                .withSymbol("ADBE")
                .withQuantity(300)
                .withDate(OffsetDateTime.now())
                .withPrice(10.0)
                .withAction(ActionGW.SELL)
                .withActionType(ActionTypeGW.STOCK)
                .build());


        List<ShareJournalGWModel> syn = generator.apply(shareList);
        Assertions.assertEquals(2, syn.size());
        syn.stream().filter(s -> s.getSymbol().equals("CSCO")).forEach(f -> {
            Assertions.assertEquals(ActionGW.SELL, f.getAction());
            Assertions.assertEquals(100, f.getQuantity());
        });

        syn.stream().filter(s -> s.getSymbol().equals("ADBE")).forEach(f -> {
            Assertions.assertEquals(ActionGW.SELL, f.getAction());
            Assertions.assertEquals(200, f.getQuantity());
        });
    }

}
