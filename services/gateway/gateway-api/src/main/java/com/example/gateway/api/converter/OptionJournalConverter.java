package com.example.gateway.api.converter;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionType;

import java.util.function.Function;

public class OptionJournalConverter {

    public static Function<OptionJournalModel, OptionJournalGWModel> toOptionGWModel = model -> OptionJournalGWModel.builder()
            .withType(TransactionTypeConverter.toTransactionTypeGW.apply(model.getTransactionDetails().getType()))
            .withTransactionId(model.getTransactionDetails().getId())
            .withAccountId(model.getTransactionDetails().getAccountId())
            .withStockSymbol(model.getTransactionDetails().getSymbol())
            .withDate(model.getTransactionDetails().getDate())
            .withAction(ActionConverter.toActionGW.apply(model.getAction()))
            .withOptionType(OptionTypeConverter.toOptionTypeGW.apply(model.getOptionType()))
            .withBrokerFees(model.getBrokerFees())
            .withContracts(model.getContracts())
            .withExpiryDate(model.getExpiryDate())
            .withPremium(model.getPremium())
            .withStockPrice(model.getStockPrice())
            .withStrikePrice(model.getStrikePrice())
            .withGroupSelected(model.getTransactionDetails().getSettings().getGroupSelected())
            .withLegClosed(model.getTransactionDetails().getSettings().getLegClosed())
            .build();

    public static Function<OptionJournalGWModel, OptionJournalModel> toOptionModel = model -> {
        TransactionModel transactionModel = TransactionModel.builder()
                .withId(model.getTransactionId())
                .withAccountId(model.getAccountId())
                .withSymbol(model.getStockSymbol())
                .withDate(model.getDate())
                .withType(TransactionType.OPTION)
                .build();

        return OptionJournalModel.builder()
                .withTransactionModel(transactionModel)
                .withAction(ActionConverter.toAction.apply(model.getAction()))
                .withActionType(OptionTypeConverter.toOptionType.apply(model.getOptionType()))
                .withBrokerFees(model.getBrokerFees())
                .withContracts(model.getContracts())
                .withExpiryDate(model.getExpiryDate())
                .withPremium(model.getPremium())
                .withStockPrice(model.getStockPrice())
                .withStrikePrice(model.getStrikePrice())
                .build();
    };
}
