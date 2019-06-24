package com.example.web.converter;

import com.example.gateway.api.model.TradeLogModelGW;
import com.example.web.model.TradeLogModel;

import java.util.function.Function;

public class TradeLogModelConverter implements Function<TradeLogModelGW, TradeLogModel> {

    @Override
    public TradeLogModel apply(TradeLogModelGW tradeLogModelGW) {
        TradeLogModel model = new TradeLogModel();
        model.setOptionList(tradeLogModelGW.getOptionList());
        model.setShareList(tradeLogModelGW.getShareList());
        model.setDividendList(tradeLogModelGW.getDividendList());
        return model;
    }
}
