package com.example.gateway.api.core.service

import arrow.core.Either
import com.example.common.exception.ApiException
import com.example.common.exception.ApiExceptionCode
import com.example.gateway.api.core.model.AccountModel
import com.example.gateway.api.rest.gateway.AccountGateway
import org.springframework.stereotype.Service

@Service
class AccountService(private val accountGateway: AccountGateway) {

    fun getActiveAccount(username: String, password: String): Either<ApiException, AccountModel> {
        return accountGateway
                .getAccount(username, password)
                .fold(
                        { throw it },
                        {
                            if (it.active)
                                Either.Right(it)
                            else
                                Either.Left(ApiException(ApiExceptionCode.ACCOUNT_NOT_ACTIVE, "Account $username is not active"))
                        }
                )

    }
}