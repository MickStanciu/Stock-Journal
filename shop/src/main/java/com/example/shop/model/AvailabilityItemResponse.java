package com.example.shop.model;

import com.example.common.rest.dto.ErrorDto;

import java.util.List;

public class AvailabilityItemResponse {

    private List<AvailabilityItem> data;
    private List<ErrorDto> errors;

    public AvailabilityItemResponse() {
    }

    public List<AvailabilityItem> getData() {
        return data;
    }

    public void setData(List<AvailabilityItem> data) {
        this.data = data;
    }

    public List<ErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDto> errors) {
        this.errors = errors;
    }
}
