package com.example.tradelog.api.spec.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.immutables.value.Value;

import java.util.List;

@Value.Style(
        init = "with*"
)
@Value.Immutable
@JsonDeserialize(builder = OptionTransactionsResponse.Builder.class)
public interface OptionTransactionsResponse {
    List<OptionJournalModel> getOptionItems();

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "with")
    class Builder extends ImmutableOptionTransactionsResponse.Builder {}
}
