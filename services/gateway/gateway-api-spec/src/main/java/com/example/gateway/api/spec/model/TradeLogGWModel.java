package com.example.gateway.api.spec.model;

import com.example.gateway.api.spec.StatisticsGWModel;

import java.util.List;

public class TradeLogGWModel {

    private List<OptionJournalGWModel> optionList;
    private List<ShareJournalGWModel> shareList;
    private List<DividendGWModel> dividendList;
    private StatisticsGWModel statistics;

    public List<OptionJournalGWModel> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<OptionJournalGWModel> optionList) {
        this.optionList = optionList;
    }

    public List<ShareJournalGWModel> getShareList() {
        return shareList;
    }

    public void setShareList(List<ShareJournalGWModel> shareList) {
        this.shareList = shareList;
    }

    public List<DividendGWModel> getDividendList() {
        return dividendList;
    }

    public void setDividendList(List<DividendGWModel> dividendList) {
        this.dividendList = dividendList;
    }

    public StatisticsGWModel getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsGWModel statistics) {
        this.statistics = statistics;
    }
}
