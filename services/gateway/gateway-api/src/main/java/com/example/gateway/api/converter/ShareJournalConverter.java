package com.example.gateway.api.converter;

import com.example.common.converter.TimeConverter;
import com.example.tradelog.api.spec.model.ShareJournalModel;

import java.util.function.Function;

public class ShareJournalConverter {

    public static Function<ShareJournalModel, ShareJournalGWModel> toShareGWModel = model -> ShareJournalGWModel.builder()
            .withType(TransactionTypeConverter.toTransactionTypeGW.apply(model.getTransactionDetails().getType()))
            .withTransactionId(model.getTransactionDetails().getId())
            .withAccountId(model.getTransactionDetails().getAccountId())
            .withDate(TimeConverter.toOffsetDateTime.apply(model.getTransactionDetails().getDate()))
            .withSymbol(model.getTransactionDetails().getSymbol())
            .withAction(ActionConverter.toActionGW.apply(model.getAction()))
            .withBrokerFees(model.getTransactionDetails().getBrokerFees())
            .withQuantity(model.getQuantity())
            .withPrice(model.getPrice())
            .withActualPrice(model.getActualPrice())
            .withPreferredPrice(model.getTransactionDetails().getSettings().getPreferredPrice())
            .withGroupSelected(model.getTransactionDetails().getSettings().getGroupSelected())
            .withLegClosed(model.getTransactionDetails().getSettings().getLegClosed())
            .build();

    public static Function<ShareJournalGWModel, ShareJournalModel> toShareModel = model -> {
        String transactionId = model.getTransactionId();
        if (transactionId == null) {
            transactionId = "";
        }

//        TransactionModel transactionModel = new TransactionModel(transactionId, model.getAccountId(), model.getDate(),
//                model.getSymbol(), TransactionType.SHARE, model.getBrokerFees(),
//                new TransactionSettingsModel(transactionId, 0.00, false, false)
//        );
//
//        return new ShareJournalModel(transactionModel, model.getPrice(), model.getActualPrice(),
//                model.getQuantity(), ActionConverter.toAction.apply(model.getAction()));
        return ShareJournalModel.newBuilder().build();
    };
}
