package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.TradeLogModel
import com.example.gateway.api.spec.model.GWTradeLogDto
import java.util.stream.Collectors

class TradeLogConverter {

    companion object {
        fun toDto(model: TradeLogModel): GWTradeLogDto {

            val shareList = model.shareList.stream()
                    .map { ShareJournalConverter.toGWDto(it) }
                    .collect(Collectors.toList())

            val optionList = model.optionList.stream()
                    .map { OptionJournalConverter.toGWDto(it) }
                    .collect(Collectors.toList())

            val dividendList = model.dividendList.stream()
                    .map { DividendJournalConverter.toGWDto(it) }
                    .collect(Collectors.toList())

            return GWTradeLogDto.newBuilder()
                    .addAllShareList(shareList)
                    .addAllOptionList(optionList)
                    .addAllDividendList(dividendList)
                    .build()
        }
    }
}