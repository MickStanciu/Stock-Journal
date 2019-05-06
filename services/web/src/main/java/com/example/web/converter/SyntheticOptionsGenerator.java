package com.example.web.converter;

import com.example.gateway.api.model.OptionJournalGWModel;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class SyntheticOptionsGenerator implements Function<List<OptionJournalGWModel>, List<OptionJournalGWModel>> {

    @Override
    public List<OptionJournalGWModel> apply(List<OptionJournalGWModel> optionJournalGWModels) {
        return Collections.emptyList();
    }
}
