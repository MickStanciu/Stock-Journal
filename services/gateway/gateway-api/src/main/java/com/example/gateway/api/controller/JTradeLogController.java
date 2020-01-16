//package com.example.gateway.api.controller;
//
//import com.example.gateway.api.exception.ExceptionCode;
//import com.example.gateway.api.exception.GatewayApiException;
//import com.example.gateway.api.service.JTradeLogService;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//
//@RestController
//@RequestMapping(value = "/api/v1/tradelog", produces = "application/json")
//public class JTradeLogController {
//
//    private JTradeLogService tradeLogService;
//
//    public JTradeLogController(JTradeLogService tradeLogService) {
//        this.tradeLogService = tradeLogService;
//    }
//
//    @RequestMapping(value = "/shares/{id}", method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.OK)
//    public void updateShareTrade(
//            @RequestHeader(name = "accountId") String accountId,
//            @PathVariable(name = "id") String transactionId,
//            @RequestBody ShareJournalGWModel model) {
//        tradeLogService.updateShareTrade(accountId, transactionId, model);
//    }
//

//}
