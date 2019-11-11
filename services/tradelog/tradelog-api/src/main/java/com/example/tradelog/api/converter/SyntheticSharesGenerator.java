package com.example.tradelog.api.converter;

import com.example.common.converter.TimeConverter;
import com.example.tradelog.api.spec.model.ActionType;
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
                .filter(f -> !f.getTransactionDetails().getSettings().getLegClosed())
                .forEach(s -> {
                    int quantity;
                    if (ActionType.BUY == s.getAction()) {
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
                        double preferredPrice = s.getTransactionDetails().getSettings().getPreferredPrice();
                        aggregator.setPreferredPrice(preferredPrice);
                    }

                    aggregator.addQuantityAndPrice(quantity, s.getPrice());
                    stocks.put(s.getTransactionDetails().getSymbol(), aggregator);
                });

        List<ShareJournalModel> synthetics = new ArrayList<>();
        stocks.forEach( (s, aggregator) -> {
            if (aggregator.getQuantity() != 0) {

                double averageBoughtPrice = aggregator.getAverageBoughtPrice();

                ActionType syntheticActionType = ActionType.SELL;
                if (aggregator.getQuantity() < 0) {
                    syntheticActionType = ActionType.BUY;
                }

                TransactionSettingsModel optionsModel = new TransactionSettingsModel("", aggregator.getPreferredPrice(), true, false);

                TransactionModel transactionModel = new TransactionModel(
                        "",
                        "",
                        TimeConverter.nextYear(),
                        s,
                        TransactionType.SYNTHETIC_SHARE,
                        0.00,
                        optionsModel);


                synthetics.add(new ShareJournalModel(transactionModel, averageBoughtPrice, aggregator.getActualPrice(),
                        Math.abs(aggregator.getQuantity()), syntheticActionType));
            }
        });

        return synthetics;

    };

}
