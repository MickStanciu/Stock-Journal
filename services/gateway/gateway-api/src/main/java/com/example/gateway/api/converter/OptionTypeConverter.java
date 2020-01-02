package com.example.gateway.api.converter;

import com.example.tradelog.api.spec.model.OptionType;

import java.util.Optional;
import java.util.function.Function;

public class OptionTypeConverter {

    static Function<OptionType, OptionTypeGW> toOptionTypeGW = optionType -> {
        Optional<OptionTypeGW> optionTypeGWOptional = OptionTypeGW.fromValue(optionType.name());
        if (optionTypeGWOptional.isEmpty()) {
            return null;
        }
        return optionTypeGWOptional.get();
    };

    static Function<OptionTypeGW, OptionType> toOptionType = optionType -> OptionType.valueOf(optionType.name());
}
