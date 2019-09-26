package com.example.tradelog.api.spec.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.immutables.value.Value;

import java.util.List;

@Value.Style(
        init = "with*"
)
@Value.Immutable
@JsonDeserialize(builder = DividendTransactionsResponse.Builder.class)
public interface DividendTransactionsResponse {
    List<DividendJournalModel> getDividendItems();

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "with")
    class Builder extends ImmutableDividendTransactionsResponse.Builder {}
}
