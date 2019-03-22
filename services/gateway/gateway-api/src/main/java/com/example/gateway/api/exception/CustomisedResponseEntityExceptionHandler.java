package com.example.gateway.api.exception;

import com.example.gateway.api.model.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@RestController
public class CustomisedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionModel> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionModel exceptionModel = new ExceptionModel(ExceptionCode.UNKNOWN, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionModel);
    }

    @ExceptionHandler(RestClientException.class)
    public final ResponseEntity<ExceptionModel> handleRestExceptions(RestClientException ex, WebRequest request) {
        ExceptionModel exceptionModel = new ExceptionModel(ExceptionCode.API_NOT_RESPONDING, ex.getRootCause().toString(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionModel);
    }

    @ExceptionHandler(GatewayApiException.class)
    public final ResponseEntity<ExceptionModel> handleGatewayApiExceptions(GatewayApiException ex, WebRequest request) {
        ExceptionModel exceptionModel = new ExceptionModel(ex.getCode(), ex.getMessage(), null);

        switch (ex.getCode()) {
            case REQUEST_NOT_AUTHORIZED:
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionModel);
            case ACCOUNT_NOT_FOUND:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionModel);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionModel);
        }

    }
}
