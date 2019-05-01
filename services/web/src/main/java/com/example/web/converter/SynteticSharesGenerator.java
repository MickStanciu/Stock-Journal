package com.example.web.converter;

import com.example.gateway.api.model.ShareJournalGWModel;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class SynteticSharesGenerator implements Function<List<ShareJournalGWModel>, List<ShareJournalGWModel>> {

    @Override
    public List<ShareJournalGWModel> apply(List<ShareJournalGWModel> shareJournalGWModels) {
        return Collections.emptyList();
    }
}
