package com.example.common.types


sealed class Either<out E, out V> {

    class Error<E>(val error: E): Either<E, Nothing>()
    class Value<V>(val value: V): Either<Nothing, V>()

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

    fun errorOrNull(): E? =
            when (this) {
                is Error -> error
                is Value -> null
            }

    companion object {
        fun <E,V,V2> Either<E,V>.bind(f: (V) -> Either<E, V2>): Either<E, V2> =
                when (this) {
                    is Error -> Error(error)
                    is Value -> f(value)
                }

        fun <E,E2,V> Either<E,V>.mapError(e: (E) -> E2): Either<E2, V> =
                when (this) {
                    is Error -> Error(e(error))
                    is Value -> this
                }

        fun <E,V,V2> Either<E,V>.mapValue(f: (V) -> V2): Either<E, V2> =
                when (this) {
                    is Error -> this
                    is Value -> Value(f(value))
                }

        fun <E,V,U> Either<E,V>.either(e: (E) -> U, v: (V) -> U): U =
                when (this) {
                    is Error -> e(error)
                    is Value -> v(value)
                }

        fun <E,V> performSafeCall(f: () -> V, g: (message: String) -> E): Either<E, V> =
                try {
                    Either.Value(f())
                } catch (exception: Exception) {
                    Either.Error(g(exception.toString()))
                }

        //    fun <E2,V2> mapErrorAndValue(f: (E) -> E2, g: (V) -> V2): Either<E2, V2> =
//        when (this) {
//            is Error -> Error(f(error))
//            is Value -> Value(g(value))
//        }

    }
}
