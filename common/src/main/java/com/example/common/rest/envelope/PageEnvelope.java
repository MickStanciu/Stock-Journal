package com.example.common.rest.envelope;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import java.util.List;

public class PageEnvelope<T> extends RestEnvelope<T> {

    private final PaginationModel pagination;


    private PageEnvelope(PageEnvelopeBuilder<T> builder) {
        super(builder.data, builder.errors);
        this.pagination = builder.pagination;
    }

    public PaginationModel getPagination() {
        return pagination;
    }

    @JsonIgnoreType
    public static class PageEnvelopeBuilder<T> {
        private T data;
        private List<ErrorModel> errors;
        private PaginationModel pagination;

        public PageEnvelope.PageEnvelopeBuilder data(T data) {
            this.data = data;
            return this;
        }

        public PageEnvelope.PageEnvelopeBuilder errors(List<ErrorModel> errors) {
            this.errors = errors;
            return this;
        }

        public PageEnvelope.PageEnvelopeBuilder pagination(PaginationModel pagination) {
            this.pagination = pagination;
            return this;
        }

        public PageEnvelope build() {
            return new PageEnvelope<>(this);
        }
    }
}
