package com.example.gateway.api.converter;

import com.example.gateway.api.model.DividendGWModel;
import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;
import com.example.gateway.api.model.TradeLogGWModel;
import com.example.tradelog.api.spec.model.TradeLogModel;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TradeLogModelConverter implements Function<TradeLogModel, TradeLogGWModel> {

    @Override
    public TradeLogGWModel apply(TradeLogModel tradeLogModel) {

        TradeLogGWModel tradeLogGWModel = new TradeLogGWModel();

        List<OptionJournalGWModel> optionList = tradeLogModel.getOptionList().stream()
                .map(OptionJournalConverter.toOptionGWModel)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<ShareJournalGWModel> shareList = tradeLogModel.getShareList().stream()
                .map(ShareJournalConverter.toShareGWModel)
                .collect(Collectors.toList());

        List<DividendGWModel> dividendList = tradeLogModel.getDividendList().stream()
                .map(new DividendJournalConverter())
                .collect(Collectors.toList());

        tradeLogGWModel.setOptionList(optionList);
        tradeLogGWModel.setShareList(shareList);
        tradeLogGWModel.setDividendList(dividendList);
        return tradeLogGWModel;
    }
}
