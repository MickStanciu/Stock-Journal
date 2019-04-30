package com.example.gateway.api.converter;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.tradelog.api.spec.model.OptionJournalModel;

import java.util.function.Function;

public class OptionJournalConverter implements Function<OptionJournalModel, OptionJournalGWModel>  {

    @Override
    public OptionJournalGWModel apply(OptionJournalModel model) {

        ActionConverter actionConverter = new ActionConverter();
        ActionTypeConverter actionTypeConverter = new ActionTypeConverter();

        return OptionJournalGWModel.builder()
                .withAccountId(model.getAccountId())
                .withAction(actionConverter.apply(model.getAction()))
                .withActionType(actionTypeConverter.apply(model.getActionType()))
                .withBrokerFees(model.getBrokerFees())
                .withContracts(model.getContracts())
                .withDate(model.getDate())
                .withExpiryDate(model.getExpiryDate())
                .withHistoricalImpliedVolatility(model.getHistoricalImpliedVolatility())
                .withImpliedVolatility(model.getImpliedVolatility())
                .withMark(model.getMark())
                .withPairTransactionId(model.getPairTransactionId())
                .withPremium(model.getPremium())
                .withProfitProbability(model.getProfitProbability())
                .withStockPrice(model.getStockPrice())
                .withStockSymbol(model.getStockSymbol())
                .withStrikePrice(model.getStrikePrice())
                .withTransactionId(model.getTransactionId())
                .build();
    }
}
