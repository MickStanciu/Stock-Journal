package com.example.gateway.api.controller;

import com.example.gateway.api.service.RabbitMQService;
import com.example.gateway.api.service.StockDataService;
import com.example.gateway.api.spec.model.ShareDataGWModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/stockdata", produces = "application/json")
public class StockDataController {

    private StockDataService stockDataService;
    private RabbitMQService rabbitMQService;

    public StockDataController(StockDataService stockDataService, RabbitMQService rabbitMQService) {
        this.stockDataService = stockDataService;
        this.rabbitMQService = rabbitMQService;
    }

    @RequestMapping(value = "/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ShareDataGWModel getDataBySymbol(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol
    ) {
        return stockDataService.getData(accountId, symbol);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void testRabbitMq() {
        rabbitMQService.send("BAU BAU");
    }

}
