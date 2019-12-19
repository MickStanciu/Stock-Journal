package com.example.tradelog.api.converter.protobuf;

import java.util.function.Function;

public class ActionTypeConverter {
    public static Function<com.example.tradelog.api.spec.model.ActionType, com.example.tradelog.api.core.model.ActionType> toModel = protoModel -> {
        return com.example.tradelog.api.core.model.ActionType.Companion.lookup(protoModel.name());
    };

    public static Function<com.example.tradelog.api.core.model.ActionType, com.example.tradelog.api.spec.model.ActionType> toProto = apiModel -> {
        return com.example.tradelog.api.spec.model.ActionType.valueOf(apiModel.name());
    };
}
