package com.example.tradelog.api.converter;

import com.example.tradelog.api.core.model.ActionType;
import com.example.tradelog.api.core.model.ShareJournalModel;
import com.example.tradelog.api.core.model.TransactionModel;
import com.example.tradelog.api.core.model.TransactionSettingsModel;
import com.example.tradelog.api.core.model.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

class SyntheticSharesGeneratorTest {

    private List<ShareJournalModel> shareList;

    @BeforeEach
    void init() {
        this.shareList = new ArrayList<>();
    }

    @Test
    void testWhereThereIsNoNeedToGenerate() {
        TransactionSettingsModel settingsModel = new TransactionSettingsModel("1234", 0.0, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, 0, settingsModel);

        shareList.add(new ShareJournalModel(transactionModel, 10.00, 10.00, 500, ActionType.BUY));
        shareList.add(new ShareJournalModel(transactionModel, 10.00, 60.00, 500, ActionType.SELL));

        List<ShareJournalModel> syn = SyntheticSharesGenerator.createSynthetic.apply(shareList);
        Assertions.assertEquals(0, syn.size());
    }


    @Test
    void testAveragePrice_1() {
        TransactionSettingsModel settingsModel = new TransactionSettingsModel("1234", 0.0, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, 0, settingsModel);

        shareList.add(new ShareJournalModel(transactionModel, 55.00, 60.00, 100, ActionType.BUY));
        shareList.add(new ShareJournalModel(transactionModel, 66.00, 60.00, 200, ActionType.BUY));

        List<ShareJournalModel> syn = SyntheticSharesGenerator.createSynthetic.apply(shareList);
        Assertions.assertEquals(1, syn.size());

        ShareJournalModel xyz = syn.get(0);
        Assertions.assertEquals(ActionType.SELL, xyz.getAction());
        Assertions.assertEquals(300, xyz.getQuantity());
        Assertions.assertEquals(62.333333333333336, xyz.getPrice());
        Assertions.assertEquals(60.0, xyz.getActualPrice());
        Assertions.assertEquals(Double.valueOf(0.0), Double.valueOf(xyz.getTransactionDetails().getSettings().getPreferredPrice()));
    }


    @Test
    void testAveragePrice_2() {
        TransactionSettingsModel settingsModel_1 = new TransactionSettingsModel("1234", 99.00, false, false);
        TransactionModel transactionModel_1 = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, 0, settingsModel_1);

        TransactionSettingsModel settingsModel_2 = new TransactionSettingsModel("1234", 99.00, false, true);
        TransactionModel transactionModel_2 = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, 0, settingsModel_2);


        shareList.add(new ShareJournalModel(transactionModel_1, 55.00, 60.00, 100, ActionType.BUY));
        shareList.add(new ShareJournalModel(transactionModel_1, 45.00, 60.00, 50, ActionType.BUY));
        shareList.add(new ShareJournalModel(transactionModel_2, 80.00, 60.00, 550, ActionType.BUY));

        List<ShareJournalModel> syn = SyntheticSharesGenerator.createSynthetic.apply(shareList);
        Assertions.assertEquals(1, syn.size());

        ShareJournalModel xyz = syn.get(0);
        Assertions.assertEquals(ActionType.SELL, xyz.getAction());
        Assertions.assertEquals(150, xyz.getQuantity());
        Assertions.assertEquals(51.666666666666664, xyz.getPrice());
        Assertions.assertEquals(60.0, xyz.getActualPrice());
        Assertions.assertEquals(Double.valueOf(99.0), Double.valueOf(xyz.getTransactionDetails().getSettings().getPreferredPrice()));
    }


    @Test
    void testSynCSCO100AndADBE200() {
        TransactionSettingsModel settingsModel = new TransactionSettingsModel("1234", 44.00, false, false);
        TransactionModel transactionModel_1 = new TransactionModel("1234", "123", OffsetDateTime.now(), "CSCO", TransactionType.SHARE, 0, settingsModel);
        TransactionModel transactionModel_2 = new TransactionModel("1234", "123", OffsetDateTime.now(), "ADBE", TransactionType.SHARE, 0, settingsModel);

        shareList.add(new ShareJournalModel(transactionModel_1, 10.00, 60.00, 500, ActionType.BUY));
        shareList.add(new ShareJournalModel(transactionModel_2, 10.00, 60.00, 500, ActionType.BUY));
        shareList.add(new ShareJournalModel(transactionModel_1, 10.00, 60.00, 400, ActionType.SELL));
        shareList.add(new ShareJournalModel(transactionModel_2, 10.00, 60.00, 300, ActionType.SELL));

        List<ShareJournalModel> syn = SyntheticSharesGenerator.createSynthetic.apply(shareList);
        Assertions.assertEquals(2, syn.size());
        syn.stream().filter(s -> s.getTransactionDetails().getSymbol().equals("CSCO")).forEach(f -> {
            Assertions.assertEquals(ActionType.SELL, f.getAction());
            Assertions.assertEquals(100, f.getQuantity());
        });

        syn.stream().filter(s -> s.getTransactionDetails().getSymbol().equals("ADBE")).forEach(f -> {
            Assertions.assertEquals(ActionType.SELL, f.getAction());
            Assertions.assertEquals(200, f.getQuantity());
        });
    }



    @Test
    void testSingleBuyTrade() {
        TransactionSettingsModel settingsModel = new TransactionSettingsModel("1234", 44.00, false, false);
        TransactionModel transactionModel = new TransactionModel("1234", "123", OffsetDateTime.now(), "XYZ", TransactionType.SHARE, 0, settingsModel);

        ShareJournalModel model = new ShareJournalModel(transactionModel, 10, 60.00, 100, ActionType.BUY);

        shareList.add(model);

        List<ShareJournalModel> newList = SyntheticSharesGenerator.createSynthetic.apply(shareList);
        Assertions.assertEquals(1, newList.size());

        ShareJournalModel synthetic = newList.get(0);
        Assertions.assertEquals(model.getTransactionDetails().getSymbol(), synthetic.getTransactionDetails().getSymbol());
        Assertions.assertEquals(TransactionType.SYNTHETIC_SHARE, synthetic.getTransactionDetails().getType());
        Assertions.assertEquals(ActionType.SELL, synthetic.getAction());
        Assertions.assertEquals(model.getQuantity(), synthetic.getQuantity());
    }
}
