package com.example.gatewayapi.gateway;

import com.example.common.rest.dto.ErrorDto;

import java.util.List;

public class ResponseEnvelope<T> {

    private T data;
    private List<ErrorDto> errors;

    public ResponseEnvelope() {
        //required by jackson
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDto> errors) {
        this.errors = errors;
    }
}
