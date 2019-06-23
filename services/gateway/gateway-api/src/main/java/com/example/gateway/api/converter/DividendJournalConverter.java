package com.example.gateway.api.converter;

import com.example.gateway.api.model.DividendGWModel;
import com.example.tradelog.api.spec.model.DividendJournalModel;

import java.util.function.Function;

public class DividendJournalConverter implements Function<DividendJournalModel, DividendGWModel> {

    @Override
    public DividendGWModel apply(DividendJournalModel model) {
        return DividendGWModel.builder()
                .withTransactionId(model.getTransactionModel().getId())
                .withDividend(model.getDividend())
                .build();
    }
}
