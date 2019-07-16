package com.example.tradelog.api.converter;

import com.example.tradelog.api.spec.model.*;

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
                    }

                    aggregator.addQuantityAndPrice(quantity, s.getPrice());
                    stocks.put(s.getTransactionDetails().getSymbol(), aggregator);
                });

        List<ShareJournalModel> synthetics = new ArrayList<>();
        stocks.forEach( (s, aggregator) -> {
            if (aggregator.getQuantity() != 0) {

                double calculatedPrice = aggregator.getActualPrice();
                if (calculatedPrice == 0.00) {
                    calculatedPrice = aggregator.getAverageBoughtPrice();
                }

                Action syntheticAction = Action.SELL;
                if (aggregator.getQuantity() < 0) {
                    syntheticAction = Action.BUY;
                }

                TransactionOptionsModel optionsModel = new TransactionOptionsModel();
                optionsModel.setGroupSelected(true);

                TransactionModel transactionModel = TransactionModel.builder()
                        .withSymbol(s)
                        .withDate(OffsetDateTime.now().plusYears(1))
                        .withType(TransactionType.SYNTHETIC_SHARE)
                        .withTransactionOptionsModel(optionsModel)
                        .build();
                synthetics.add(ShareJournalModel.builder()
                        .withTransactionModel(transactionModel)
                        .withQuantity(aggregator.getQuantity())
                        .withPrice(calculatedPrice)
                        .withActualPrice(calculatedPrice)
                        .withAction(syntheticAction)
                        .build());
            }
        });

        return synthetics;

    };

}
