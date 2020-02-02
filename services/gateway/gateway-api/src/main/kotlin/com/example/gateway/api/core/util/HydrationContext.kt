package com.example.gateway.api.core.util

import com.example.gateway.api.core.model.ShareJournalModel
import com.example.gateway.api.core.model.SharePriceModel

class HydrationContext(
        val symbol: String,
        val shareList: ArrayList<ShareJournalModel>,
        var priceModel: SharePriceModel) {
}