package com.example.gateway.api.core.model

class TradeLogModel(
        val shareList: List<ShareJournalModel>,
        val optionList: List<OptionJournalModel>,
        val dividendList: List<DividendJournalModel>
)
