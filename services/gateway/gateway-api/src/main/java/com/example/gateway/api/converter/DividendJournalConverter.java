package com.example.gateway.api.converter;

import com.example.gateway.api.model.DividendGWModel;
import com.example.tradelog.api.spec.model.DividendJournalModel;
import com.example.tradelog.api.spec.model.TransactionModel;
import com.example.tradelog.api.spec.model.TransactionType;

import java.util.function.Function;

public class DividendJournalConverter {

    public static Function<DividendJournalModel, DividendGWModel> toDividendGWModel = model ->
        DividendGWModel.builder()
                .withType(TransactionTypeConverter.toTransactionTypeGW.apply(model.getTransactionDetails().getType()))
                .withTransactionId(model.getTransactionDetails().getId())
                .withDividend(model.getDividend())
                .withQuantity(model.getQuantity())
                .withAccountId(model.getTransactionDetails().getAccountId())
                .withDate(model.getTransactionDetails().getDate())
                .withSymbol(model.getTransactionDetails().getSymbol())
                .withGroupSelected(model.getTransactionDetails().getSettings().isGroupSelected())
                .withLegClosed(model.getTransactionDetails().getSettings().isLegClosed())
                .build();


    public static Function<DividendGWModel, DividendJournalModel> toDividendModel = model -> {
        TransactionModel transactionModel = TransactionModel.builder()
                .withId(model.getTransactionId())
                .withAccountId(model.getAccountId())
                .withSymbol(model.getSymbol())
                .withDate(model.getDate())
                .withType(TransactionType.DIVIDEND)
                .build();

        return DividendJournalModel.builder()
                .withTransactionDetails(transactionModel)
                .withDividend(model.getDividend())
                .withQuantity(model.getQuantity())
                .build();
    };
}
