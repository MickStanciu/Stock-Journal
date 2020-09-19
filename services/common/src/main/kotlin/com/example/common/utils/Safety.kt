package com.example.common.utils

import arrow.core.Either

fun <L,R> performSafeCall(fn: () -> R, efn: (message: String) -> L): Either<L, R> =
        try {
            Either.Right(fn())
        } catch (exception: Exception) {
            Either.Left(efn(exception.toString()))
        }
