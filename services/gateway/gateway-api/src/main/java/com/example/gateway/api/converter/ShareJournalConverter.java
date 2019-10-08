package com.example.gateway.api.converter;

import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.tradelog.api.spec.model.ShareJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;
import com.example.tradelog.api.spec.model.TransactionType;

import java.util.function.Function;

public class ShareJournalConverter {

    public static Function<ShareJournalModel, ShareJournalGWModel> toShareGWModel = model -> ShareJournalGWModel.builder()
            .withType(TransactionTypeConverter.toTransactionTypeGW.apply(model.getTransactionDetails().getType()))
            .withTransactionId(model.getTransactionDetails().getId())
            .withAccountId(model.getTransactionDetails().getAccountId())
            .withDate(model.getTransactionDetails().getDate())
            .withSymbol(model.getTransactionDetails().getSymbol())
            .withAction(ActionConverter.toActionGW.apply(model.getAction()))
            .withBrokerFees(model.getBrokerFees())
            .withQuantity(model.getQuantity())
            .withPrice(model.getPrice())
            .withActualPrice(model.getActualPrice())
            .withPreferredPrice(model.getTransactionDetails().getSettings().getPreferredPrice())
            .withGroupSelected(model.getTransactionDetails().getSettings().getGroupSelected())
            .withLegClosed(model.getTransactionDetails().getSettings().getLegClosed())
            .build();

    public static Function<ShareJournalGWModel, ShareJournalModel> toShareModel = model -> {
        TransactionModel transactionModel = new TransactionModel(model.getTransactionId(), model.getAccountId(), model.getDate(),
                model.getSymbol(), TransactionType.SHARE,
                new TransactionSettingsModel(model.getTransactionId(), 0.00, false, false)
        );

        return new ShareJournalModel(transactionModel, model.getPrice(), model.getActualPrice(),
                model.getQuantity(), ActionConverter.toAction.apply(model.getAction()),
                model.getBrokerFees());
    };
}
