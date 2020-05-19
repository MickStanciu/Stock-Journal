package com.example.common.validator

class LongValidator(private val value: Long): Validator {

    private var result = true

    fun greaterThanZero(): LongValidator {
        result = result and (value > 0)
        return this
    }

    override fun isValid(): Boolean {
        return result
    }
}