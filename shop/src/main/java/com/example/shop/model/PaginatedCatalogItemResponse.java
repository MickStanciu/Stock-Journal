package com.example.shop.model;

import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.dto.PaginationDto;

import java.util.List;

public class PaginatedCatalogItemResponse {

    private List<ErrorDto> errors;
    private List<CatalogItem> data;
    private PaginationDto pagination;

    public PaginatedCatalogItemResponse() {
    }

    public List<ErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDto> errors) {
        this.errors = errors;
    }

    public List<CatalogItem> getData() {
        return data;
    }

    public void setData(List<CatalogItem> data) {
        this.data = data;
    }

    public PaginationDto getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDto pagination) {
        this.pagination = pagination;
    }
}
