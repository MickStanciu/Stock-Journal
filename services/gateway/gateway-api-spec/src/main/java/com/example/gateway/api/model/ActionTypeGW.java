package com.example.gateway.api.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public enum ActionTypeGW {
    UNKNOWN,
    STOCK,
    PUT_OPTION,
    CALL_OPTION;

    private static final Set<String> VALUES = new HashSet<>();

    static {
        Arrays.stream(ActionTypeGW.values()).forEach(e -> VALUES.add(e.name()));
    }

    public static Optional<ActionTypeGW> fromValue(String value) {
        if (VALUES.contains(value)) {
            return Optional.of(ActionTypeGW.valueOf(value));
        }
        return Optional.empty();
    }
}
