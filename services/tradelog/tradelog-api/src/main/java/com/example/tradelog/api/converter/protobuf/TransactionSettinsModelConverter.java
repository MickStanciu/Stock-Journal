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
}
