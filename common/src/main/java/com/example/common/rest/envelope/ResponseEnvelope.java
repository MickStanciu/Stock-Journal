package com.example.common.rest.envelope;

import com.example.common.rest.dto.ErrorDto;

import java.util.List;

public class ResponseEnvelope<T> extends RestEnvelope<T> {

    ResponseEnvelope(Builder<T> builder) {
        super(builder.data, builder.errors);
    }

    public static class Builder<T> {
        private T data;
        private List<ErrorDto> errors;

        public Builder withData(T data) {
            this.data = data;
            return this;
        }

        public Builder withErrors(List<ErrorDto> errors) {
            this.errors = errors;
            return this;
        }

        public ResponseEnvelope build() {
            return new ResponseEnvelope<>(this);
        }
    }
}
