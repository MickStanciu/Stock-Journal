package com.example.gateway.api.converter;

import com.example.gateway.api.model.ActionGW;
import com.example.gateway.api.model.ActionTypeGW;
import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class OptionJournalConverter {

    public static Function<OptionJournalModel, OptionJournalGWModel> gwModelConverter = p -> {

        Optional<ActionGW> actionGWOptional = ActionGW.fromValue(p.getAction().name());
        if (actionGWOptional.isEmpty()) {
            return null;
        }

        Optional<ActionTypeGW> actionTypeGWOptional = ActionTypeGW.fromValue(p.getActionType().name());
        if (actionTypeGWOptional.isEmpty()) {
            return null;
        }

        return OptionJournalGWModel.builder()
                .withAccountId(p.getAccountId())
                .withAction(actionGWOptional.get())
                .withActionType(actionTypeGWOptional.get())
                .withBrokerFees(p.getBrokerFees())
                .withContracts(p.getContracts())
                .withDate(p.getDate())
                .withExpiryDate(p.getExpiryDate())
                .withHistoricalImpliedVolatility(p.getHistoricalImpliedVolatility())
                .withImpliedVolatility(p.getImpliedVolatility())
                .withMark(p.getMark())
                .withPairTransactionId(p.getPairTransactionId())
                .withPremium(p.getPremium())
                .withProfitProbability(p.getProfitProbability())
                .withStockPrice(p.getStockPrice())
                .withStockSymbol(p.getStockSymbol())
                .withStrikePrice(p.getStrikePrice())
                .withTransactionId(p.getTransactionId())
                .build();
    };
}
