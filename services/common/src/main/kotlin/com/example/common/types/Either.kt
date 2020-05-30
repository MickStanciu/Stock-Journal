package com.example.common.types


sealed class Either<out L, out R> {

    class Left<out L>(val value: L): Either<L, Nothing>()
    class Right<out R>(val value: R): Either<Nothing, R>()

    fun isLeft(): Boolean =
            when (this) {
                is Left -> true
                is Right -> false
            }

    fun isRight(): Boolean =
            when (this) {
                is Left -> false
                is Right -> true
            }

    fun rightOrNull(): R? =
            when (this) {
                is Left -> null
                is Right -> this.value
            }

    fun rightOrThrow(lfn: (L) -> Throwable): R =
            when (this) {
                is Left -> throw lfn(this.value)
                is Right -> this.value
            }

    fun leftOrNull(): L? =
            when (this) {
                is Left -> this.value
                is Right -> null
            }

    fun <T> fold(lfn: (L) -> T, rfn: (R) -> T): T =
            when (this) {
                is Left -> lfn(this.value)
                is Right -> rfn(this.value)
            }

    fun <L2> mapLeft(lfn: (L) -> L2): Either<L2, R> =
            when (this) {
                is Left -> Left(lfn(this.value))
                is Right -> this
            }

    fun <R2> mapRight(rfn: (R) -> R2): Either<L, R2> =
            when (this) {
                is Left -> this
                is Right -> Right(rfn(this.value))
            }

    companion object {
        fun <L,R,R2> Either<L,R>.bind(fn: (R) -> Either<L, R2>): Either<L, R2> =
                when (this) {
                    is Left -> this
                    is Right -> fn(this.value)
                }
    }
}
