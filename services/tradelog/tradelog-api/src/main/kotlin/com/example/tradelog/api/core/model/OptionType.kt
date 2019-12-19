package com.example.tradelog.api.core.model

enum class OptionType {
    UNKNOWN,
    PUT,
    CALL;

    companion object {
        fun lookup(id: String) : OptionType {
            return try {
                valueOf(id)
            } catch (ex: IllegalArgumentException) {
                UNKNOWN
            }
        }
    }
}
