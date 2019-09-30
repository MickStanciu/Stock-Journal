package com.example.tradelog.api.spec.model

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
