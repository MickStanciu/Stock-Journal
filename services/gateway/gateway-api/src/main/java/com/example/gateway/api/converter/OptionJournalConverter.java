package com.example.gateway.api.converter;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.tradelog.api.spec.model.OptionJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
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
        String transactionId = model.getTransactionId();
        if (transactionId == null) {
            transactionId = "";
        }

        TransactionModel transactionModel = new TransactionModel(transactionId, model.getAccountId(), model.getDate(),
                model.getStockSymbol(), TransactionType.OPTION,
                new TransactionSettingsModel(transactionId, 0.00, false, false)
        );

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
