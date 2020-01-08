package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.ActionType
import com.example.gateway.api.spec.model.GWShareJournalDto
import com.example.tradelog.api.spec.model.ShareJournalDto as TLShareJournalDto

class ActionTypeConverter {

    companion object {
        fun toModel(dto: TLShareJournalDto.ActionType): ActionType {
            return ActionType.lookup(dto.name)
        }

        fun toDto(model: ActionType): GWShareJournalDto.ActionType {
            return GWShareJournalDto.ActionType.valueOf(model.name)
        }
    }
}