package com.example.gateway.api.converter;

import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.tradelog.api.spec.model.ShareJournalModel;

import java.util.function.Function;

public class ShareJournalConverter implements Function<ShareJournalModel, ShareJournalGWModel> {

    @Override
    public ShareJournalGWModel apply(ShareJournalModel model) {

        ActionConverter actionConverter = new ActionConverter();
        ActionTypeConverter actionTypeConverter = new ActionTypeConverter();

        return ShareJournalGWModel.builder()
                .withTransactionId(model.getTransactionModel().getId())
                .withAccountId(model.getTransactionModel().getAccountId())
                .withDate(model.getTransactionModel().getDate())
                .withSymbol(model.getTransactionModel().getSymbol())
                .withAction(actionConverter.apply(model.getAction()))
                .withActionType(actionTypeConverter.apply(model.getActionType()))
                .withBrokerFees(model.getBrokerFees())
                .withQuantity(model.getQuantity())
                .withPrice(model.getPrice())
                .build();
    }
}
