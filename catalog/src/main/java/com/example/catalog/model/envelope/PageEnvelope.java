package com.example.catalog.model.envelope;

import com.example.common.rest.envelope.RestEnvelope;
import com.example.catalog.model.PaginationDto;
import com.example.common.rest.envelope.ErrorDto;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import java.util.List;

public class PageEnvelope<T> extends RestEnvelope<T> {

    private final PaginationDto pagination;


    PageEnvelope(PageEnvelopeBuilder<T> builder) {
        super(builder.data, builder.errors);
        this.pagination = builder.pagination;
    }

    public PaginationDto getPagination() {
        return pagination;
    }

    @JsonIgnoreType
    public static class PageEnvelopeBuilder<T> {
        private T data;
        private List<ErrorDto> errors;
        private PaginationDto pagination;

        public PageEnvelope.PageEnvelopeBuilder data(T data) {
            this.data = data;
            return this;
        }

        public PageEnvelope.PageEnvelopeBuilder errors(List<ErrorDto> errors) {
            this.errors = errors;
            return this;
        }

        public PageEnvelope.PageEnvelopeBuilder pagination(PaginationDto pagination) {
            this.pagination = pagination;
            return this;
        }

        public PageEnvelope build() {
            return new PageEnvelope<>(this);
        }
    }
}
