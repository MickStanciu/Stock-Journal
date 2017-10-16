package com.example.shop.model;

import com.example.common.rest.envelope.ErrorDto;

import java.util.List;

public class CatalogItemResponse {

    private CatalogItem data;
    private List<ErrorDto> errors;

    public CatalogItemResponse() {
    }

    public CatalogItem getData() {
        return data;
    }

    public List<ErrorDto> getErrors() {
        return errors;
    }

    public void setData(CatalogItem data) {
        this.data = data;
    }

    public void setErrors(List<ErrorDto> errors) {
        this.errors = errors;
    }
}
