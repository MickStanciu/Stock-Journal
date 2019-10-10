package com.example.web.converter;

import com.example.gateway.api.spec.model.ActionGW;
import com.example.gateway.api.spec.model.OptionTypeGW;
import com.example.gateway.api.spec.model.OptionJournalGWModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class SyntheticOptionsGenerator implements Function<List<OptionJournalGWModel>, List<OptionJournalGWModel>> {

    @Override
    public List<OptionJournalGWModel> apply(List<OptionJournalGWModel> optionJournalGWModels) {
        Map<String, OptionJournalGWModel> options = new HashMap<>();
        Set<String> keysToRemove = new HashSet<>();

        OptionConverterUtil optionConverterUtil = new OptionConverterUtil();

        optionJournalGWModels.stream()
            .filter(f -> f.getOptionType().equals(OptionTypeGW.CALL) || f.getOptionType().equals(OptionTypeGW.PUT))
            .forEach(o -> {
                String key = optionConverterUtil.generateName.apply(o);
                String oppositeKey = optionConverterUtil.generateOppositeActionName.apply(o);

                OptionJournalGWModel model;
                if (options.containsKey(oppositeKey) ) {
                    //need to remove it
                    keysToRemove.add(oppositeKey);
                } else {
                    model = options.getOrDefault(key, o);
                    options.put(key, model);
                }
            });

        //remove unnecessary keys
        keysToRemove.forEach(options::remove);

        List<OptionJournalGWModel> syntethics = new ArrayList<>();
        options.forEach( (k, o) -> {
            if (o.getContracts() != 0) {

                ActionGW newAction = ActionGW.UNKNOWN;
                if (ActionGW.SELL == o.getAction()) {
                    newAction = ActionGW.BUY;
                } else if (ActionGW.BUY == o.getAction()) {
                    newAction = ActionGW.SELL;
                }

                syntethics.add(OptionJournalGWModel.builder()
                        .withStockSymbol(o.getStockSymbol())
                        .withStrikePrice(o.getStrikePrice())
                        .withAction(newAction)
                        .withOptionType(o.getOptionType())
                        .withContracts(o.getContracts())
                        .withExpiryDate(o.getExpiryDate())
                        .withDate(o.getExpiryDate())
                        .build());
            }
        });

        return syntethics;
    }

}
