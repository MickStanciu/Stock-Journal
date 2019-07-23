package com.example.gateway.api.converter;

import com.example.gateway.api.model.TransactionSettingsGWModel;
import com.example.tradelog.api.spec.model.TransactionSettingsModel;

import java.util.function.Function;

public class TransactionSettingConverter {

    public static Function<TransactionSettingsGWModel, TransactionSettingsModel> toTransactionSettinsGWModel = gwModel -> {
        TransactionSettingsModel model = new TransactionSettingsModel();
        model.setTransactionId(gwModel.getTransactionId());
        model.setGroupSelected(gwModel.isGroupSelected());
        model.setLegClosed(gwModel.isLegClosed());
        return model;
    };
}
