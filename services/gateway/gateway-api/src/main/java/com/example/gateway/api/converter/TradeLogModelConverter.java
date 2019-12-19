package com.example.gateway.api.converter;

import com.example.gateway.api.spec.model.DividendGWModel;
import com.example.gateway.api.spec.model.OptionJournalGWModel;
import com.example.gateway.api.spec.model.ShareJournalGWModel;
import com.example.gateway.api.spec.model.TradeLogGWModel;

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
                .map(DividendJournalConverter.toDividendGWModel)
                .collect(Collectors.toList());

        tradeLogGWModel.setOptionList(optionList);
        tradeLogGWModel.setShareList(shareList);
        tradeLogGWModel.setDividendList(dividendList);

        tradeLogGWModel.setStatistics(null);
        return tradeLogGWModel;
    }
}
