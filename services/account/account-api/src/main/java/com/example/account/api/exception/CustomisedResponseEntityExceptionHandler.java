package com.example.account.api.exception;

import com.example.account.api.spec.exception.ExceptionCode;
import com.example.account.api.spec.model.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomisedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionModel> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionModel exceptionModel = new ExceptionModel(ExceptionCode.UNKNOWN, ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionModel);
    }

    @ExceptionHandler(AccountException.class)
    public final ResponseEntity<ExceptionModel> handleAllExceptions(AccountException ex, WebRequest request) {
        ExceptionModel exceptionModel = new ExceptionModel(ex.getCode(), ex.getMessage(), request.getDescription(false));

        switch (ex.getCode()) {
            case BAD_REQUEST:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionModel);
            case ACCOUNT_NOT_FOUND:
            case ACCOUNTS_NOT_FOUND:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionModel);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionModel);
        }
    }
}
