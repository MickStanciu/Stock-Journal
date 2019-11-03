package com.example.tradelog.api.converter.protobuf;

import com.example.common.converter.TimeConverter;

import java.util.function.Function;

public class TransactionModelConverter {
    public static Function<com.example.tradelog.api.proto3.model.TransactionModel, com.example.tradelog.api.spec.model.TransactionModel> toModel = protoModel -> {
        return new com.example.tradelog.api.spec.model.TransactionModel(
                protoModel.getId(),
                protoModel.getAccountId(),
                TimeConverter.toOffsetDateTime.apply((protoModel.getDate())),
                protoModel.getSymbol(),
                TransactionTypeConverter.toModel.apply(protoModel.getType()),
                protoModel.getBrokerFees(),
                TransactionSettinsModelConverter.toModel.apply(protoModel.getSettings())
        );
    };

    public static Function<com.example.tradelog.api.spec.model.TransactionModel, com.example.tradelog.api.proto3.model.TransactionModel> toProto = apiModel -> {
        return com.example.tradelog.api.proto3.model.TransactionModel.newBuilder()
                .build();
    };
}

