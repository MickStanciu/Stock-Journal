package com.example.gateway.api.model;

import java.util.List;

public class TradeLogModelGW {

    private List<OptionJournalGWModel> optionList;
    private List<ShareJournalGWModel> shareList;

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
}
