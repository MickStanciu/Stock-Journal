package com.example.tradelog.api.spec.model

enum class Action {
    UNKNOWN,
    SELL,
    BUY;

    companion object {
        fun lookup(id: String) : Action {
            return try {
                valueOf(id)
            } catch (ex: IllegalArgumentException) {
                UNKNOWN
            }
        }
    }
}
