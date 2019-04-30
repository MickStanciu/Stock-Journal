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
                .withAccountId(model.getAccountId())
                .withAction(actionConverter.apply(model.getAction()))
                .withActionType(actionTypeConverter.apply(model.getActionType()))
                .withBrokerFees(model.getBrokerFees())
                .withQuantity(model.getQuantity())
                .withDate(model.getDate())
                .withMark(model.getMark())
                .withPrice(model.getPrice())
                .withSymbol(model.getSymbol())
                .withTransactionId(model.getTransactionId())
                .build();
    }
}
