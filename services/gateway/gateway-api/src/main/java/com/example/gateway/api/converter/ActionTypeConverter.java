package com.example.gateway.api.converter;

import com.example.gateway.api.model.ActionTypeGW;
import com.example.tradelog.api.spec.model.ActionType;

import java.util.Optional;
import java.util.function.Function;

public class ActionTypeConverter implements Function<ActionType, ActionTypeGW> {
    @Override
    public ActionTypeGW apply(ActionType actionType) {
        Optional<ActionTypeGW> actionTypeGWOptional = ActionTypeGW.fromValue(actionType.name());
        if (actionTypeGWOptional.isEmpty()) {
            return null;
        }
        return actionTypeGWOptional.get();
    }
}
