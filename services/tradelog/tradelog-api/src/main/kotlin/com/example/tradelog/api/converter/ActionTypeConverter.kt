package com.example.tradelog.api.converter;

import com.example.tradelog.api.spec.model.ActionType;

import java.util.function.Function;

public class ActionTypeConverter {

    Function<com.example.tradelog.api.proto3.model.ActionType, com.example.tradelog.api.spec.model.ActionType> toActionType = protoModel -> {
      return ActionType.Companion.lookup(protoModel.getValueDescriptor().getName());
    };
}
