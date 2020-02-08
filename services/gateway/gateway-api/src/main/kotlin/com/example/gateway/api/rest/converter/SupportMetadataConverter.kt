package com.example.gateway.api.rest.converter

import com.example.gateway.api.core.model.SupportMetadata

class SupportMetadataConverter() {

    companion object {
        fun build(accountId: String): SupportMetadata {
            return SupportMetadata(accountId = accountId)
        }
    }
}