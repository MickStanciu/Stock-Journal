package com.example.common.validator;

public class StringValidator implements Validator {

    private final String string;
    private boolean result;

    public StringValidator(String string) {
        this.result = true;
        this.string = string;
    }

    public StringValidator notNull() {
        this.result &= this.string != null;
        return this;
    }

    public StringValidator sizeEqualTo(int size) {
        this.result &= this.string != null && this.string.length() == size;
        return this;
    }

    public StringValidator sizeLessOrEqualTo(int size) {
        this.result &= this.string != null && this.string.length() <= size;
        return this;
    }

    public StringValidator sizeGreaterOrEqualTo(int size) {
        this.result &= this.string != null && this.string.length() >= size;
        return this;
    }

    @Override
    public boolean isValid() {
        return this.result;
    }
}
