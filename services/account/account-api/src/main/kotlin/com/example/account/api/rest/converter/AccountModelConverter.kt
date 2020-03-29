package com.example.account.api.rest.converter

import com.example.account.api.spec.model.AAccountDto
import com.example.account.api.spec.model.AccountModel

class AccountModelConverter {

    companion object {
        fun toDto(model: AccountModel): AAccountDto  {
            return AAccountDto.newBuilder()
                    .setActive(model.isActive)
                    .setEmail(model.email)
                    .setDisplayName(model.name)
                    .setLoginName(model.name)
                    .build()
        }
    }
}