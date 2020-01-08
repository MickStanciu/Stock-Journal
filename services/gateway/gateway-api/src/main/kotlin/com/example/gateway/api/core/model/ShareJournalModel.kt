package com.example.gateway.api.core.model

class ShareJournalModel(val transactionId: String,
                        val symbol: String) {
}
/*
    string account_id = 2;
    string date = 3;
    double price = 5;
    double actual_price = 6;
    double preferred_price = 7;
    int64 quantity = 8;
    double broker_fees = 9;
    bool group_selected = 10;
    bool leg_closed = 11;
    ActionType action = 12;
    TransactionType type = 13;
 */