package com.example.web.model;

import com.example.gateway.api.model.DividendGWModel;
import com.example.gateway.api.model.OptionJournalGWModel;
import com.example.gateway.api.model.ShareJournalGWModel;

import java.util.List;

public class TradeLogModel {
    private List<OptionJournalGWModel> optionList;
    private List<ShareJournalGWModel> shareList;
    private List<ShareJournalGWModel> syntheticShareList;
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

    public List<ShareJournalGWModel> getSyntheticShareList() {
        return syntheticShareList;
    }

    public void setSyntheticShareList(List<ShareJournalGWModel> syntheticShareList) {
        this.syntheticShareList = syntheticShareList;
    }

    public List<DividendGWModel> getDividendList() {
        return dividendList;
    }

    public void setDividendList(List<DividendGWModel> dividendList) {
        this.dividendList = dividendList;
    }
}
