package com.example.web.exception;

import com.example.gateway.api.exception.ExceptionCode;
import com.example.gateway.api.model.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomisedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionModel> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionModel exceptionModel = new ExceptionModel(ExceptionCode.UNKNOWN, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionModel);
    }

    @ExceptionHandler(RestClientException.class)
    public final ResponseEntity<ExceptionModel> handleRestExceptions(RestClientException ex, WebRequest request) {
        String cause = ex.getMessage();
        if (ex.getRootCause() != null) {
            cause = ex.getRootCause().toString();
        }
        ExceptionModel exceptionModel = new ExceptionModel(ExceptionCode.API_NOT_RESPONDING, cause, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionModel);
    }
}
