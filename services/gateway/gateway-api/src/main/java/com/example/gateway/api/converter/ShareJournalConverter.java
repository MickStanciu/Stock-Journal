package com.example.gateway.api.converter;

import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.tradelog.api.spec.model.ShareJournalModel;

import java.util.function.Function;

public class ShareJournalConverter implements Function<ShareJournalModel, ShareJournalGWModel> {

    @Override
    public ShareJournalGWModel apply(ShareJournalModel model) {

        return ShareJournalGWModel.builder()
                .withTransactionId(model.getTransactionDetails().getId())
                .withAccountId(model.getTransactionDetails().getAccountId())
                .withDate(model.getTransactionDetails().getDate())
                .withSymbol(model.getTransactionDetails().getSymbol())
                .withAction(ActionConverter.toActionGW.apply(model.getAction()))
                .withActionType(ActionTypeConverter.toActionTypeGW.apply(model.getActionType()))
                .withBrokerFees(model.getBrokerFees())
                .withQuantity(model.getQuantity())
                .withPrice(model.getPrice())
                .build();
    }
}
