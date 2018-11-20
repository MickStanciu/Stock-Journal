//package com.example.common.rest.envelope;
//
//import java.io.Serializable;
//import java.util.List;
//
//public abstract class RestEnvelope<T> implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    private T data;
//    private List<ErrorModel> errors;
//
//    RestEnvelope(T data, List<ErrorModel> errors) {
//        this.data = data;
//        this.errors = errors;
//    }
//
//    public RestEnvelope() {
//        //required by jackson
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public List<ErrorModel> getErrors() {
//        return errors;
//    }
//}
