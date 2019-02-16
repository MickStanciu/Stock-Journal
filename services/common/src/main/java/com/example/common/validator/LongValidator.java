package com.example.common.validator;

public class LongValidator implements Validator {
    private final long value;
    private boolean result;

    public LongValidator(long value) {
        this.result = true;
        this.value = value;
    }

    public LongValidator greaterThanZero() {
        this.result &= this.value > 0;
        return this;
    }

    @Override
    public boolean isValid() {
        return this.result;
    }
}
