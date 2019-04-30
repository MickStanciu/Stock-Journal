package com.example.gateway.api.converter;

import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.gateway.api.model.TradeLogModelGW;
import com.example.tradelog.api.spec.model.TradeLogModel;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TradeLogModelConverter implements Function<TradeLogModel, TradeLogModelGW> {

    @Override
    public TradeLogModelGW apply(TradeLogModel tradeLogModel) {

        TradeLogModelGW tradeLogModelGW = new TradeLogModelGW();

        List<OptionJournalGWModel> optionList = tradeLogModel.getOptionList().stream()
                .map(new OptionJournalConverter())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<ShareJournalGWModel> shareList = tradeLogModel.getShareList().stream()
                .map(new ShareJournalConverter())
                .collect(Collectors.toList());

        tradeLogModelGW.setOptionList(optionList);
        tradeLogModelGW.setShareList(shareList);
        return tradeLogModelGW;
    }
}
