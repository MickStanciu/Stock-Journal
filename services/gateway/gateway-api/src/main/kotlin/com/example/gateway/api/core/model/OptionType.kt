package com.example.gateway.api.core.model

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
