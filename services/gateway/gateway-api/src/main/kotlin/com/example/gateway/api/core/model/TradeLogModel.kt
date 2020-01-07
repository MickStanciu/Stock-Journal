package com.example.gateway.api.core.model

class TradeLogModel(
        val shareList: List<ShareJournalModel>,
        private val optionList: List<OptionJournalModel>,
        private val dividendList: List<DividendJournalModel>)