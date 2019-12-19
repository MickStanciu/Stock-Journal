package com.example.tradelog.api.converter.protobuf;


import java.util.function.Function;

public class TransactionTypeConverter {

    public static Function<com.example.tradelog.api.spec.model.TransactionType, com.example.tradelog.api.core.model.TransactionType> toModel = protoModel -> {
        return com.example.tradelog.api.core.model.TransactionType.Companion.lookup(protoModel.name());
    };

    public static Function<com.example.tradelog.api.core.model.TransactionType, com.example.tradelog.api.spec.model.TransactionType> toProto = apiModel -> {
        return com.example.tradelog.api.spec.model.TransactionType.valueOf(apiModel.name());
    };
}
