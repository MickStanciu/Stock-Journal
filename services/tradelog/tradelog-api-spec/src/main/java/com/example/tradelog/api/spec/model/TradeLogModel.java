package com.example.tradelog.api.spec.model;

import java.util.List;

public class TradeLogModel {

    private List<OptionJournalModel> optionList;
    private List<ShareJournalModel> shareList;
    private List<DividendJournalModel> dividendList;

    public List<OptionJournalModel> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<OptionJournalModel> optionList) {
        this.optionList = optionList;
    }

    public List<ShareJournalModel> getShareList() {
        return shareList;
    }

    public void setShareList(List<ShareJournalModel> shareList) {
        this.shareList = shareList;
    }

    public List<DividendJournalModel> getDividendList() {
        return dividendList;
    }

    public void setDividendList(List<DividendJournalModel> dividendList) {
        this.dividendList = dividendList;
    }
}
