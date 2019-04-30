package com.example.gateway.api.converter;

import com.example.gateway.api.model.ActionGW;
import com.example.tradelog.api.spec.model.Action;

import java.util.Optional;
import java.util.function.Function;

public class ActionConverter implements Function<Action, ActionGW> {
    @Override
    public ActionGW apply(Action action) {
        Optional<ActionGW> actionGWOptional = ActionGW.fromValue(action.name());
        if (actionGWOptional.isEmpty()) {
            return null;
        }
        return actionGWOptional.get();
    }
}
