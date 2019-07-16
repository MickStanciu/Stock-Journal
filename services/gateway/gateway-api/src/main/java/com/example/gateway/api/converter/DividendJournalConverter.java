package com.example.gateway.api.converter;

import com.example.gateway.api.model.DividendGWModel;
import com.example.tradelog.api.spec.model.DividendJournalModel;

import java.util.function.Function;

public class DividendJournalConverter implements Function<DividendJournalModel, DividendGWModel> {

    @Override
    public DividendGWModel apply(DividendJournalModel model) {
        return DividendGWModel.builder()
                .withType(TransactionTypeConverter.toTransactionTypeGW.apply(model.getTransactionDetails().getType()))
                .withTransactionId(model.getTransactionDetails().getId())
                .withDividend(model.getDividend())
                .withAccountId(model.getTransactionDetails().getAccountId())
                .withDate(model.getTransactionDetails().getDate())
                .withSymbol(model.getTransactionDetails().getSymbol())
                .withGroupEnabled(model.getTransactionDetails().getOptions().isGroupSelected())
                .build();
    }
}
