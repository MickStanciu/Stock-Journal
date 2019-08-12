package com.example.gateway.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.io.Serializable;

@JsonDeserialize(builder = StatisticsGWModel.Builder.class)
public class StatisticsGWModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "with")
    public static class Builder {
        StatisticsGWModel model;

        Builder() {
            model = new StatisticsGWModel();
        }

        public StatisticsGWModel build() {
            StatisticsGWModel buildModel = this.model;
            this.model = new StatisticsGWModel();
            return buildModel;
        }
    }
}
