package com.example.common.utils

import com.example.common.types.Either

fun <L,R> performSafeCall(fn: () -> R, efn: (message: String) -> L): Either<L, R> =
        try {
            Either.Right(fn())
        } catch (exception: Exception) {
            Either.Left(efn(exception.toString()))
        }
