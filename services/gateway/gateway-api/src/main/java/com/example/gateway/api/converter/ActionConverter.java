package com.example.gateway.api.converter;

import com.example.gateway.api.spec.model.ActionGW;
import com.example.tradelog.api.spec.model.ActionType;

import java.util.Optional;
import java.util.function.Function;

public class ActionConverter {

    static Function<ActionType, ActionGW> toActionGW = action -> {
        Optional<ActionGW> actionGWOptional = ActionGW.fromValue(action.name());
        if (actionGWOptional.isEmpty()) {
            return null;
        }
        return actionGWOptional.get();
    };

    static Function<ActionGW, ActionType> toAction = action -> ActionType.Companion.lookup(action.name());



}
