package com.example.gateway.api.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public enum OptionTypeGW {
    UNKNOWN,
    PUT,
    CALL;

    private static final Set<String> VALUES = new HashSet<>();

    static {
        Arrays.stream(OptionTypeGW.values()).forEach(e -> VALUES.add(e.name()));
    }

    public static Optional<OptionTypeGW> fromValue(String value) {
        if (VALUES.contains(value)) {
            return Optional.of(OptionTypeGW.valueOf(value));
        }
        return Optional.empty();
    }
}
