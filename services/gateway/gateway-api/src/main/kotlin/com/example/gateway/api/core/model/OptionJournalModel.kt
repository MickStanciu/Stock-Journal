package com.example.gateway.api.core.model

class OptionJournalModel(val transactionId: String,
                         val stockSymbol: String)

/*
    string transaction_id = 1;
    string account_id = 2;
    string date = 3;
    string stock_symbol = 4;
    double stock_price = 5;
    double strike_price = 6;
    string expiry_date = 7;
    int64 contracts = 8;
    double premium = 9;
    double broker_fees = 10;
    bool group_selected = 11;
    bool leg_closed = 12;
    ActionType action = 13;
    OptionType option_type = 14;
    TransactionType type = 15;

    enum OptionType {
        UNKNOWN = 0;
        PUT = 1;
        CALL = 2;
    }
 */