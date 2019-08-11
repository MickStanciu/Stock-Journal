package com.example.tradelog.api.converter;

import com.example.tradelog.api.spec.model.Action;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import com.example.tradelog.api.spec.model.TransactionType;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SyntheticSharesGenerator {

    public static Function<List<ShareJournalModel>, List<ShareJournalModel>> createSynthetic = shareJournalModels -> {
        Map<String, ShareAggregator> stocks = new HashMap<>();

        shareJournalModels.stream()
                .filter(f -> f.getTransactionDetails().getType().equals(TransactionType.SHARE))
                .filter(f -> !f.getTransactionDetails().getSettings().isLegClosed())
                .forEach(s -> {
                    int quantity;
                    if (Action.BUY == s.getAction()) {
                        quantity = s.getQuantity();
                    } else {
                        quantity = s.getQuantity() * -1;
                    }

                    ShareAggregator aggregator;
                    if (stocks.containsKey(s.getTransactionDetails().getSymbol())) {
                        aggregator = stocks.get(s.getTransactionDetails().getSymbol());
                    } else {
                        aggregator = new ShareAggregator(s.getTransactionDetails().getSymbol());
                        aggregator.setActualPrice(s.getActualPrice());
                        Double preferredPrice = s.getTransactionDetails().getSettings().getPreferredPrice();
                        aggregator.setPreferredPrice(preferredPrice);
                    }

                    aggregator.addQuantityAndPrice(quantity, s.getPrice());
                    stocks.put(s.getTransactionDetails().getSymbol(), aggregator);
                });

        List<ShareJournalModel> synthetics = new ArrayList<>();
        stocks.forEach( (s, aggregator) -> {
            if (aggregator.getQuantity() != 0) {

                double calculatedPrice = aggregator.getActualPrice();
                double averageBoughtPrice = aggregator.getAverageBoughtPrice();

                Action syntheticAction = Action.SELL;
                if (aggregator.getQuantity() < 0) {
                    syntheticAction = Action.BUY;
                }

                TransactionSettingsModel optionsModel = TransactionSettingsModel.builder()
                        .withPreferredPrice(null)
                        .withGroupSelected(true)
                        .withLegClosed(false)
                        .withPreferredPrice(aggregator.getPreferredPrice())
                        .build();

                TransactionModel transactionModel = TransactionModel.builder()
                        .withSymbol(s)
                        .withDate(OffsetDateTime.now().plusYears(1))
                        .withType(TransactionType.SYNTHETIC_SHARE)
                        .withSettings(optionsModel)
                        .build();

                synthetics.add(ShareJournalModel.builder()
                        .withTransactionModel(transactionModel)
                        .withQuantity(Math.abs(aggregator.getQuantity()))
                        .withPrice(averageBoughtPrice)
                        .withActualPrice(aggregator.getActualPrice())
                        .withAction(syntheticAction)
                        .build());
            }
        });

        return synthetics;

    };

}
