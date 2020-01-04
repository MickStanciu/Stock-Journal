//package com.example.gateway.api.converter;
//
//import com.example.common.converter.TimeConverter;
//import com.example.tradelog.api.spec.model.DividendJournalModel;
//
//import java.util.function.Function;
//
//public class DividendJournalConverter {
//
//    public static Function<DividendJournalModel, DividendGWModel> toDividendGWModel = model ->
//        DividendGWModel.builder()
//                .withType(TransactionTypeConverter.toTransactionTypeGW.apply(model.getTransactionDetails().getType()))
//                .withTransactionId(model.getTransactionDetails().getId())
//                .withDividend(model.getDividend())
//                .withQuantity(model.getQuantity())
//                .withAccountId(model.getTransactionDetails().getAccountId())
//                .withDate(TimeConverter.toOffsetDateTime.apply(model.getTransactionDetails().getDate()))
//                .withSymbol(model.getTransactionDetails().getSymbol())
//                .withGroupSelected(model.getTransactionDetails().getSettings().getGroupSelected())
//                .withLegClosed(model.getTransactionDetails().getSettings().getLegClosed())
//                .build();
//
//
//    public static Function<DividendGWModel, DividendJournalModel> toDividendModel = model -> {
//        String transactionId = model.getTransactionId();
//        if (transactionId == null) {
//            transactionId = "";
//        }
//
////        TransactionModel transactionModel = new TransactionModel(transactionId, model.getAccountId(), model.getDate(),
////                model.getSymbol(), TransactionType.DIVIDEND, 0.00,
////                new TransactionSettingsModel(transactionId, 0.00, false, false)
////        );
//
//        //(transactionModel, model.getDividend(), model.getQuantity()
//        return DividendJournalModel.newBuilder().build();
//    };
//}
