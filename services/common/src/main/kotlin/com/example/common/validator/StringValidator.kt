package com.example.common.validator

class StringValidator(private val string: String): Validator {

    private var result = true

    fun notEmpty(): StringValidator {
        result = result and string.trim().isNotEmpty()
        return this
    }

    fun sizeEqualTo(size: Int): StringValidator {
        result = result and (string.length == size)
        return this
    }

    fun sizeLessOrEqualTo(size: Int): StringValidator {
        result = result and (string.length <= size)
        return this
    }

    fun sizeGreaterOrEqualTo(size: Int): StringValidator {
        result = result and (string.length >= size)
        return this
    }

    fun regex(pattern: String): StringValidator {
        result = result and string.matches(Regex.fromLiteral(pattern))
        return this
    }

    override fun isValid(): Boolean {
        return result
    }
}