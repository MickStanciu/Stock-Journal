package com.example.model;

import java.io.Serializable;

public class ErrorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String code;
    private final String message;

    public ErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
