package com.example.common.rest.envelope;

import java.util.List;

public class ResponseEnvelope<T> extends RestEnvelope<T> {

    private ResponseEnvelope(Builder<T> builder) {
        super(builder.data, builder.errors);
    }

    public ResponseEnvelope() {
        //required by jackson
    }

    public static class Builder<T> {
        private T data;
        private List<ErrorModel> errors;

        public Builder withData(T data) {
            this.data = data;
            return this;
        }

        public Builder withErrors(List<ErrorModel> errors) {
            this.errors = errors;
            return this;
        }

        public ResponseEnvelope build() {
            return new ResponseEnvelope<>(this);
        }
    }
}
