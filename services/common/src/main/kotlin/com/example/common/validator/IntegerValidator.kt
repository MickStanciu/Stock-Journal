package com.example.common.validator

class IntegerValidator(private val integer: Int): Validator {
    private var result = true

    fun greaterThanZero(): IntegerValidator {
        result = result and (integer > 0)
        return this
    }

    override fun isValid(): Boolean {
        return result
    }
}