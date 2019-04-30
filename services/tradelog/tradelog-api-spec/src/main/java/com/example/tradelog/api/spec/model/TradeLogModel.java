package com.example.tradelog.api.spec.model;

import java.util.List;

public class TradeLogModel {

    private List<OptionJournalModel> optionList;
    private List<ShareJournalModel> shareList;

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
}
