package com.example.gateway.api.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public enum ActionGW {
    UNKNOWN,
    SELL_OPTION;

    private static final Set<String> VALUES = new HashSet<>();

    static {
        Arrays.stream(ActionGW.values()).forEach(e -> VALUES.add(e.name()));
    }

    public static Optional<ActionGW> fromValue(String value) {
        if (VALUES.contains(value)) {
            return Optional.of(ActionGW.valueOf(value));
        }
        return Optional.empty();
    }
}
