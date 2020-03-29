package com.example.account.api.rest.controller

import com.example.account.api.facade.AccountFacade
import com.example.account.api.rest.controller.AccountController.Companion.PROTOBUF_MEDIA_TYPE_VALUE
import com.example.account.api.rest.converter.AccountModelConverter
import com.example.account.api.rest.exception.AccountException
import com.example.account.api.rest.exception.ExceptionCode
import com.example.account.api.rest.validator.RequestValidator
import com.example.account.api.spec.model.AAccountDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/api/v1"], produces = [PROTOBUF_MEDIA_TYPE_VALUE, MediaType.APPLICATION_JSON_VALUE])
class AccountController(private val accountFacade: AccountFacade) {

    companion object {
        const val PROTOBUF_MEDIA_TYPE_VALUE = "application/x-protobuf"
        private val LOG = LoggerFactory.getLogger(AccountController::class.java)
    }


    @RequestMapping(value = ["", "/"], method = [RequestMethod.GET])
    @ResponseStatus(HttpStatus.OK)
    fun getAccountByUserAndPassword(
            @RequestParam(name = "username", defaultValue = "") username: String,
            @RequestParam(name = "password", defaultValue = "") password: String
    ): AAccountDto {
        if (!RequestValidator.validateGetAccountByUserAndPassword(username, password)) {
            throw AccountException(ExceptionCode.BAD_REQUEST)
        }
        val accountModel = accountFacade.getAccount(username, password)

        if (accountModel == null || accountModel.isEmpty) {
            throw AccountException(ExceptionCode.ACCOUNT_NOT_FOUND)
        }
        return AccountModelConverter.toDto(accountModel.get())
    }
}