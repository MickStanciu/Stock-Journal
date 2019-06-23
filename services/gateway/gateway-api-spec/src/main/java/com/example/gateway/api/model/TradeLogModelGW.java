package com.example.gateway.api.model;

import java.util.List;

public class TradeLogModelGW {

    private List<OptionJournalGWModel> optionList;
    private List<ShareJournalGWModel> shareList;
    private List<DividendGWModel> dividendList;

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
}
