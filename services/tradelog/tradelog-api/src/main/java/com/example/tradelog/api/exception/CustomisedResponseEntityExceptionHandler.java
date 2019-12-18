//package com.example.tradelog.api.exception;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@ControllerAdvice
//public class CustomisedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//    private static final Logger log = LoggerFactory.getLogger(CustomisedResponseEntityExceptionHandler.class);
//
//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<ExceptionModel> handleAllExceptions(Exception ex, WebRequest request) {
//        ExceptionModel exceptionModel = new ExceptionModel(ExceptionCode.UNKNOWN, ex.getMessage(), request.getDescription(false));
//        log.error(ex.getMessage(), ex);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionModel);
//    }
//
//    @ExceptionHandler(TradeLogException.class)
//    public final ResponseEntity<ExceptionModel> handleTradeLogExceptions(TradeLogException ex, WebRequest request) {
//        ExceptionModel exceptionModel = new ExceptionModel(ex.getCode(), ex.getMessage(), request.getDescription(false));
//        log.error(ex.getMessage(), ex);
//
//        switch (ex.getCode()) {
//            case BAD_REQUEST:
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionModel);
//            case TRADELOG_EMPTY:
//                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exceptionModel);
//            case DELETE_SHARE_FAILED:
//            case DELETE_OPTION_FAILED:
//            case SHARE_DATA_EMPTY:
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionModel);
//            default:
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionModel);
//        }
//    }
//}
