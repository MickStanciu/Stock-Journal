package com.example.stockdata.api.model;

import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Value.Style(
        init = "with*"
)
@Value.Immutable
public interface ShareDataModel {
    String getSymbol();
    OffsetDateTime getLastUpdatedOn();
    String getSector();
    double getPrice();
    double getMarketCapitalization();
    double getPeRatio();
    double getPeRatioFuture();
    double getBookValue();
    double getEps();
    double getEpsFuture();
    BigDecimal getFinvizTarget();

    @Nullable BigDecimal getCalculatedTarget();

    class Builder extends ImmutableShareDataModel.Builder {}
}
