package com.example.gateway.api.converter;

import com.example.gateway.api.model.TransactionSettingsGWModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;

import java.util.function.Function;

public class TransactionSettingConverter {

    public static Function<TransactionSettingsGWModel, TransactionSettingsModel> toTransactionSettingsGWModel =
            gwModel ->
                    TransactionSettingsModel.builder()
                            .withTransactionId(gwModel.getTransactionId())
                            .withPreferredPrice(gwModel.getPreferredPrice())
                            .withLegClosed(gwModel.isLegClosed())
                            .withGroupSelected(gwModel.isGroupSelected())
                            .build();
}
