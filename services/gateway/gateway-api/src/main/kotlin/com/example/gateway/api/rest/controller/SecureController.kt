package com.example.gateway.api.rest.controller

import com.example.gateway.api.core.model.SupportMetadata
import com.example.gateway.api.rest.converter.TokenConverter
import java.util.*

interface SecureController {

    fun getSupportData(token: String, portfolioId: UUID): SupportMetadata {
        return SupportMetadata(
                accountId = UUID.fromString(TokenConverter.decode(token)),
                portfolioId = portfolioId
        )
    }
}