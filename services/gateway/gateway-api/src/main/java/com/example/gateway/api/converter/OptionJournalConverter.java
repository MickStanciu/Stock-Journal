//package com.example.gateway.api.converter;
//
//import com.example.common.converter.TimeConverter;
//import com.example.tradelog.api.spec.model.OptionJournalModel;
//
//import java.util.function.Function;
//
//public class OptionJournalConverter {
//
//    public static Function<OptionJournalModel, OptionJournalGWModel> toOptionGWModel = model -> OptionJournalGWModel.builder()
//            .withType(TransactionTypeConverter.toTransactionTypeGW.apply(model.getTransactionDetails().getType()))
//            .withTransactionId(model.getTransactionDetails().getId())
//            .withAccountId(model.getTransactionDetails().getAccountId())
//            .withStockSymbol(model.getTransactionDetails().getSymbol())
//            .withDate(TimeConverter.toOffsetDateTime.apply(model.getTransactionDetails().getDate()))
//            .withAction(ActionConverter.toActionGW.apply(model.getAction()))
//            .withOptionType(OptionTypeConverter.toOptionTypeGW.apply(model.getOptionType()))
//            .withBrokerFees(model.getTransactionDetails().getBrokerFees())
//            .withContracts(model.getContracts())
//            .withExpiryDate(TimeConverter.toOffsetDateTime.apply(model.getExpiryDate()))
//            .withPremium(model.getPremium())
//            .withStockPrice(model.getStockPrice())
//            .withStrikePrice(model.getStrikePrice())
//            .withGroupSelected(model.getTransactionDetails().getSettings().getGroupSelected())
//            .withLegClosed(model.getTransactionDetails().getSettings().getLegClosed())
//            .build();
//
//    public static Function<OptionJournalGWModel, OptionJournalModel> toOptionModel = model -> {
//        String transactionId = model.getTransactionId();
//        if (transactionId == null) {
//            transactionId = "";
//        }
////
////        TransactionModel transactionModel = new TransactionModel(transactionId, model.getAccountId(), model.getDate(),
////                model.getStockSymbol(), TransactionType.OPTION, model.getBrokerFees(),
////                new TransactionSettingsModel(transactionId, 0.00, false, false)
////        );
////
////        return new OptionJournalModel(transactionModel, model.getStockPrice(), model.getStrikePrice(),
////                model.getExpiryDate(), model.getContracts(), model.getPremium(),
////                ActionConverter.toAction.apply(model.getAction()),
////                OptionTypeConverter.toOptionType.apply(model.getOptionType()));
//        return OptionJournalModel.newBuilder().build();
//    };
//}
