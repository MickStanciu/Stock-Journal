//package com.example.gateway.api.controller;
//
//import com.example.gateway.api.service.RabbitMQService;
//import com.example.gateway.api.service.StockDataService;
//import com.example.stockdata.api.spec.model.PriceItemUpdateRequest;
//import com.example.stockdata.api.spec.model.PriceUpdateRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping(value = "/api/v1/stockdata", produces = "application/json")
//public class StockDataController {
//

//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public void testRabbitMq() {
//        PriceUpdateRequest message = PriceUpdateRequest.newBuilder()
//                .addPrice(PriceItemUpdateRequest.newBuilder()
//                        .setSymbol("XYZ")
//                        .setLastClose(123.456)
//                        .build())
//                .build();
//        rabbitMQService.send(message);
//    }
//
//}
