package com.example.common.types


sealed class Either<out E, out V> {

    class Error<E>(val error: E): Either<E, Nothing>()
    class Value<V>(val value: V): Either<Nothing, V>()

    fun <V> value(value: V): Either<Nothing, V> = Value(value)
    fun <E> error(error: E): Either<E, Nothing> = Error(error)

    fun <U> either(f: (E) -> U, g: (V) -> U): U =
            when (this) {
                is Error -> f(error)
                is Value -> g(value)
            }

    fun <E2> mapError(f: (E) -> E2): Either<E2, V> =
            when (this) {
                is Error -> Error(f(error))
                is Value -> this
            }

    fun isError(): Boolean =
            when (this) {
                is Error -> true
                is Value -> false
            }

    fun isValue(): Boolean =
            when (this) {
                is Error -> false
                is Value -> true
            }

    fun valueOrNull(): V? =
            when (this) {
                is Error -> null
                is Value -> value
            }


}

