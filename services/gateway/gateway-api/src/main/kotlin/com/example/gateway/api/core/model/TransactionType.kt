package com.example.gateway.api.core.model

enum class TransactionType {
    SHARE,
    SYNTHETIC_SHARE,
    OPTION,
    DIVIDEND,
    UNKNOWN;

    companion object {
        fun lookup(id: String) : TransactionType {
            return try {
                valueOf(id)
            } catch (e : IllegalArgumentException) {
                UNKNOWN
            }
        }
    }
}
