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
                .withTransactionId(model.getTransactionDetails().getId())
                .withAccountId(model.getTransactionDetails().getAccountId())
                .withStockSymbol(model.getTransactionDetails().getSymbol())
                .withDate(model.getTransactionDetails().getDate())
                .withAction(actionConverter.apply(model.getAction()))
                .withActionType(actionTypeConverter.apply(model.getActionType()))
                .withBrokerFees(model.getBrokerFees())
                .withContracts(model.getContracts())
                .withExpiryDate(model.getExpiryDate())
                .withPremium(model.getPremium())
                .withStockPrice(model.getStockPrice())
                .withStrikePrice(model.getStrikePrice())
                .build();
    }
}
