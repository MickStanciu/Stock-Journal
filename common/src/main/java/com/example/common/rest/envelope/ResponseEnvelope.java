package com.example.common.rest.envelope;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import java.util.List;

public class ResponseEnvelope<T> extends RestEnvelope<T> {

    public ResponseEnvelope() {
    }

    public ResponseEnvelope(ResponseEnvelopeBuilder<T> builder) {
        super(builder.data, builder.errors);
    }

    @JsonIgnoreType
    public static class ResponseEnvelopeBuilder<T> {
        private T data;
        private List<ErrorDto> errors;

        public ResponseEnvelopeBuilder data(T data) {
            this.data = data;
            return this;
        }

        public ResponseEnvelopeBuilder errors(List<ErrorDto> errors) {
            this.errors = errors;
            return this;
        }

        public ResponseEnvelope build() {
            return new ResponseEnvelope<>(this);
        }
    }
}
