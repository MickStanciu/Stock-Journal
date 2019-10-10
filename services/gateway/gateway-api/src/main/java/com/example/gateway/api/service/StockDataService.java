package com.example.gateway.api.service;

import com.example.gateway.api.converter.ShareDataConverter;
import com.example.gateway.api.gateway.StockDataGateway;
import com.example.gateway.api.spec.model.ShareDataGWModel;
import com.example.tradelog.api.spec.model.ShareDataModel;
import org.springframework.stereotype.Service;

@Service
public class StockDataService {

    private StockDataGateway stockDataGateway;

    public StockDataService(StockDataGateway stockDataGateway) {
        this.stockDataGateway = stockDataGateway;
    }

    public ShareDataGWModel getData(String accountId, String symbol) {
        ShareDataModel returnModel = stockDataGateway.getShareDataBySymbol(accountId, symbol);
        return ShareDataConverter.toShareDataGWModel.apply(returnModel);
    }
}
