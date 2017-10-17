package com.example.common.rest.dto;

import java.io.Serializable;

public class ErrorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String message;

    public ErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDto() {
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
