package com.example.account.api.rest.converter

import com.example.account.api.core.model.AccountModel
import com.example.account.api.spec.model.AAccountDto

class AccountModelConverter {

    companion object {
        fun toDto(model: AccountModel): AAccountDto  {
            return AAccountDto.newBuilder()
                    .setId(model.id)
                    .setActive(model.active)
                    .setEmail(model.email)
                    .setDisplayName(model.displayName)
                    .setLoginName(model.loginName)
                    .build()
        }
    }
}