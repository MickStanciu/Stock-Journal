package com.example.tenant.api.exception;

import com.example.tenant.api.spec.exception.ExceptionCode;
import com.example.tenant.api.spec.model.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomisedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionModel> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionModel exceptionModel = new ExceptionModel(ExceptionCode.UNKNOWN, ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionModel);
    }

    @ExceptionHandler(TenantException.class)
    public final ResponseEntity<ExceptionModel> handleAllExceptions(TenantException ex, WebRequest request) {
        ExceptionModel exceptionModel = new ExceptionModel(ex.getCode(), ex.getMessage(), request.getDescription(false));

        switch (ex.getCode()) {
            case TENANT_NOT_FOUND:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionModel);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionModel);
        }

    }
}
