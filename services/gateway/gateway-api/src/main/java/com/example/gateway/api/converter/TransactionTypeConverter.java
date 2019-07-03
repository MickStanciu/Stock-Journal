package com.example.gateway.api.converter;

import com.example.gateway.api.model.TransactionTypeGW;
import com.example.tradelog.api.spec.model.TransactionType;

import java.util.function.Function;

public class TransactionTypeConverter {

    static Function<TransactionType, TransactionTypeGW> toTransactionTypeGW = transactionType -> TransactionTypeGW.lookup(transactionType.name());

//    static Function<ActionTypeGW, OptionType> toActionType = actionType -> OptionType.lookup(actionType.name());
}
