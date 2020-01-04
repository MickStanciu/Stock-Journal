//package com.example.gateway.api.service;
//
//import com.example.gateway.api.converter.ShareDataConverter;
//import com.example.gateway.api.gateway.StockDataGateway;
//import com.example.stockdata.api.spec.model.PriceItemResponse;
//import org.springframework.stereotype.Service;
//
//@Service
//public class StockDataService {
//
//    private StockDataGateway stockDataGateway;
//
//    public StockDataService(StockDataGateway stockDataGateway) {
//        this.stockDataGateway = stockDataGateway;
//    }
//
//    public ShareDataGWModel getData(String accountId, String symbol) {
//        PriceItemResponse returnModel = stockDataGateway.getPriceDataBySymbol(accountId, symbol);
//        return ShareDataConverter.toShareDataGWModel.apply(returnModel);
//    }
//}
