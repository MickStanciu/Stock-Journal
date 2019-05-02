package com.example.web.converter;

import com.example.gateway.api.model.ActionGW;
import com.example.gateway.api.model.ActionTypeGW;
import com.example.gateway.api.model.ShareJournalGWModel;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SyntheticSharesGenerator implements Function<List<ShareJournalGWModel>, List<ShareJournalGWModel>> {

    @Override
    public List<ShareJournalGWModel> apply(List<ShareJournalGWModel> shareJournalGWModels) {
        Map<String, Integer> stocks = new HashMap<>();
        shareJournalGWModels.stream()
                .filter(f -> f.getActionType().equals(ActionTypeGW.STOCK))
                .forEach(s -> {
                    int quantity;
                    if (ActionGW.BUY == s.getAction()) {
                        quantity = s.getQuantity();
                    } else {
                        quantity = s.getQuantity() * -1;
                    }

                    if (stocks.containsKey(s.getSymbol())) {
                        quantity += stocks.get(s.getSymbol());

                    }
                    stocks.put(s.getSymbol(), quantity);
        });

        List<ShareJournalGWModel> synthetics = new ArrayList<>();
        stocks.forEach( (s, q) -> {
            if (q != 0) {
                synthetics.add(ShareJournalGWModel.builder()
                        .withSymbol(s)
                        .withQuantity(q)
                        .withDate(OffsetDateTime.now().plusYears(1))
                        .withPrice(0.0)
                        .withAction(ActionGW.SELL)
                        .withActionType(ActionTypeGW.STOCK)
                        .build());
            }

        });

        return synthetics;
    }
}
