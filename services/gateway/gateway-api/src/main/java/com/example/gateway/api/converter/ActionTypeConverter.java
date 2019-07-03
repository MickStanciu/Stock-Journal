package com.example.gateway.api.converter;

import com.example.gateway.api.model.ActionTypeGW;
import com.example.tradelog.api.spec.model.OptionType;

import java.util.Optional;
import java.util.function.Function;

public class ActionTypeConverter {

    static Function<OptionType, ActionTypeGW> toActionTypeGW = actionType -> {
        Optional<ActionTypeGW> actionTypeGWOptional = ActionTypeGW.fromValue(actionType.name());
        if (actionTypeGWOptional.isEmpty()) {
            return null;
        }
        return actionTypeGWOptional.get();
    };

    static Function<ActionTypeGW, OptionType> toActionType = actionType -> OptionType.lookup(actionType.name());
}
