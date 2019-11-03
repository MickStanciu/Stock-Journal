package com.example.tradelog.api.converter.protobuf;

import java.util.function.Function;

public class TransactionSettinsModelConverter {
    public static Function<com.example.tradelog.api.proto3.model.TransactionSettingsModel, com.example.tradelog.api.spec.model.TransactionSettingsModel> toModel = protoModel -> {
        return new com.example.tradelog.api.spec.model.TransactionSettingsModel(
                protoModel.getTransactionId(),
                protoModel.getPreferredPrice(),
                protoModel.getGroupSelected(),
                protoModel.getLegClosed()
        );
    };

    public static Function<com.example.tradelog.api.spec.model.TransactionSettingsModel, com.example.tradelog.api.proto3.model.TransactionSettingsModel> toProto = apiModel -> {
        return com.example.tradelog.api.proto3.model.TransactionSettingsModel.newBuilder()
                .setTransactionId(apiModel.getTransactionId())
                .setPreferredPrice(apiModel.getPreferredPrice())
                .setGroupSelected(apiModel.getGroupSelected())
                .setLegClosed(apiModel.getLegClosed())
                .build();
    };
}
