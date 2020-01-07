package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.ShareJournalModel
import com.example.gateway.api.spec.model.GWShareJournalDto
import com.example.tradelog.api.spec.model.ShareJournalDto

class ShareTransactionsResponseConverter {

    companion object {
        fun toModel(dto: ShareJournalDto): ShareJournalModel {
            return ShareJournalModel(

            )
        }

        fun toDto(model: ShareJournalModel): GWShareJournalDto {
            return GWShareJournalDto.newBuilder().build()
        }
    }
}