package com.example.stockdata.api.exception;

import com.example.stockdata.api.spec.model.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomisedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomisedResponseEntityExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.newBuilder()
                .setCode(ExceptionResponse.ExceptionCode.UNKNOWN)
                .setMessage(ex.getMessage())
                .setDetails(request.getDescription(false))
                .build();
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(ShareDataException.class)
    public final ResponseEntity<ExceptionResponse> handleTradeLogExceptions(ShareDataException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.newBuilder()
                .setCode(ExceptionResponse.ExceptionCode.valueOf(ex.getCode().toString()))
                .setMessage(ex.getMessage())
                .setDetails(request.getDescription(false))
                .build();
        log.error(ex.getMessage(), ex);

        switch (ex.getCode()) {
            case BAD_REQUEST:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
            case SHARE_DATA_EMPTY:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
        }
    }
}
