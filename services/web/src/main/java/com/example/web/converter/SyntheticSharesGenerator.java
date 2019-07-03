package com.example.web.converter;

import com.example.gateway.api.model.ActionGW;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.gateway.api.model.TransactionTypeGW;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SyntheticSharesGenerator implements Function<List<ShareJournalGWModel>, List<ShareJournalGWModel>> {

    @Override
    public List<ShareJournalGWModel> apply(List<ShareJournalGWModel> shareJournalGWModels) {
        Map<String, ShareAggregator> stocks = new HashMap<>();

        shareJournalGWModels.stream()
                .filter(f -> f.getType().equals(TransactionTypeGW.SHARE))
                .forEach(s -> {
                    int quantity;
                    if (ActionGW.BUY == s.getAction()) {
                        quantity = s.getQuantity();
                    } else {
                        quantity = s.getQuantity() * -1;
                    }

                    ShareAggregator aggregator;
                    if (stocks.containsKey(s.getSymbol())) {
                        aggregator = stocks.get(s.getSymbol());
                    } else {
                        aggregator = new ShareAggregator(s.getSymbol());
                    }

                    aggregator.addQuantityAndPrice(quantity, s.getPrice());
                    stocks.put(s.getSymbol(), aggregator);
        });

        List<ShareJournalGWModel> synthetics = new ArrayList<>();
        stocks.forEach( (s, q) -> {
            if (q.getQuantity() != 0) {
                synthetics.add(ShareJournalGWModel.builder()
                        .withSymbol(s)
                        .withQuantity(q.getQuantity())
                        .withDate(OffsetDateTime.now().plusYears(1))
                        .withPrice(q.getAverageBoughtPrice())
                        .withAction(ActionGW.SELL)
                        .withType(TransactionTypeGW.SHARE)
                        .build());
            }
        });

        return synthetics;
    }
}
