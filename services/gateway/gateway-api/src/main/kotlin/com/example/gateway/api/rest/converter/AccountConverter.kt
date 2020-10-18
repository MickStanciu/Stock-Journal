package com.example.gateway.api.rest.converter

import com.example.account.api.spec.model.AAccountDto
import com.example.gateway.api.core.model.AccountModel
import java.util.*

class AccountConverter {
    companion object {
        fun toModel(dto: AAccountDto): AccountModel {
            return AccountModel(
                    id = UUID.fromString(dto.id),
                    active = dto.active,
                    displayName = dto.displayName,
                    email = dto.email,
                    loginName = dto.loginName
            )
        }
    }
}