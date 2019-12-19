package com.example.tradelog.api.converter.protobuf;


import java.util.function.Function;

public class ShareJournalModelConverter {
    public static Function<com.example.tradelog.api.spec.model.ShareJournalModel, com.example.tradelog.api.core.model.ShareJournalModel> toModel = protoModel -> {
        return new com.example.tradelog.api.core.model.ShareJournalModel(
                TransactionModelConverter.toModel.apply(protoModel.getTransactionDetails()),
                protoModel.getPrice(),
                protoModel.getActualPrice(),
                protoModel.getQuantity(),
                ActionTypeConverter.toModel.apply(protoModel.getAction())
        );
    };

    public static Function<com.example.tradelog.api.core.model.ShareJournalModel, com.example.tradelog.api.spec.model.ShareJournalModel> toProto = apiModel -> {
        return com.example.tradelog.api.spec.model.ShareJournalModel.newBuilder()
                .setTransactionDetails(TransactionModelConverter.toProto.apply(apiModel.getTransactionDetails()))
                .setPrice(apiModel.getPrice())
                .setActualPrice(apiModel.getActualPrice())
                .setQuantity(apiModel.getQuantity())
                .setAction(ActionTypeConverter.toProto.apply(apiModel.getAction()))
                .build();
    };
}
