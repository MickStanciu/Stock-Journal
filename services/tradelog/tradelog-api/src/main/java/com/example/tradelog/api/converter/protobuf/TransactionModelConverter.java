package com.example.tradelog.api.converter.protobuf;

import com.example.common.converter.TimeConverter;

import java.util.function.Function;

public class TransactionModelConverter {
    public static Function<com.example.tradelog.api.proto3.model.TransactionModel, com.example.tradelog.api.core.model.TransactionModel> toModel = protoModel -> {
        return new com.example.tradelog.api.core.model.TransactionModel(
                protoModel.getId(),
                protoModel.getAccountId(),
                TimeConverter.toOffsetDateTime.apply((protoModel.getDate())),
                protoModel.getSymbol(),
                TransactionTypeConverter.toModel.apply(protoModel.getType()),
                protoModel.getBrokerFees(),
                TransactionSettinsModelConverter.toModel.apply(protoModel.getSettings())
        );
    };

    public static Function<com.example.tradelog.api.core.model.TransactionModel, com.example.tradelog.api.proto3.model.TransactionModel> toProto = apiModel -> {
        return com.example.tradelog.api.proto3.model.TransactionModel.newBuilder()
                .setId(apiModel.getId())
                .setAccountId(apiModel.getAccountId())
                .setDate(apiModel.getDate().toString())
                .setSymbol(apiModel.getSymbol())
                .setType(TransactionTypeConverter.toProto.apply(apiModel.getType()))
                .setBrokerFees(apiModel.getBrokerFees())
                .setSettings(TransactionSettinsModelConverter.toProto.apply(apiModel.getSettings()))
                .build();
    };
}

