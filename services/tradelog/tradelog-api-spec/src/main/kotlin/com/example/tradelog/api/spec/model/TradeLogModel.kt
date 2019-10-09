package com.example.tradelog.api.spec.model

class TradeLogModel(
        val shareList: List<ShareJournalModel>,
        val optionList: List<OptionJournalModel>,
        val dividendList: List<DividendJournalModel>
)