package com.example.common.validator;

public class IntegerValidator implements Validator {
    private final Integer integer;
    private boolean result;

    public IntegerValidator(Integer integer) {
        this.result = true;
        this.integer = integer;
    }

    public IntegerValidator notNull() {
        this.result &= this.integer != null;
        return this;
    }

    public IntegerValidator greaterThanZero() {
        this.result &= this.integer > 0;
        return this;
    }

    @Override
    public boolean isValid() {
        return this.result;
    }
}
