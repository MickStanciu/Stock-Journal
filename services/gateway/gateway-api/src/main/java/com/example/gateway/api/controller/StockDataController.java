package com.example.gateway.api.controller;

import com.example.gateway.api.spec.model.ShareDataGWModel;
import com.example.gateway.api.service.StockDataService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/stockdata", produces = "application/json")
public class StockDataController {

    private StockDataService stockDataService;

    public StockDataController(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    @RequestMapping(value = "/{symbol}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ShareDataGWModel getDataBySymbol(
            @RequestHeader(name = "accountId") String accountId,
            @PathVariable(name = "symbol") String symbol
    ) {
        return stockDataService.getData(accountId, symbol);
    }


}
