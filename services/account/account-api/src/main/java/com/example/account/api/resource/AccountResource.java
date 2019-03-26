package com.example.account.api.resource;

import com.example.account.api.exception.AccountException;
import com.example.account.api.facade.AccountFacade;
import com.example.account.api.spec.exception.ExceptionCode;
import com.example.account.api.spec.model.AccountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class AccountResource {

    private static final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private AccountFacade accountFacade;

    public AccountResource(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }


    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AccountModel accountByEmailAndPassword(
            @RequestParam(name = "email", defaultValue = "")  String email,
            @RequestParam(name = "password", defaultValue = "") String password
    ) throws AccountException {
        if (!RequestValidation.validateGetAccount(email, password)) {
            throw new AccountException(ExceptionCode.BAD_REQUEST);
        }

        Optional<AccountModel> accountOptional = accountFacade.getAccount(email, password);

        if (accountOptional.isEmpty()) {
            throw new AccountException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return accountOptional.get();
    }


    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AccountModel accountById(@PathVariable("accountId") String accountId) throws AccountException {
        if (!RequestValidation.validateGetAccount(accountId)) {
            throw new AccountException(ExceptionCode.BAD_REQUEST);
        }

        Optional<AccountModel> accountOptional = accountFacade.getAccount(accountId);

        if (accountOptional.isEmpty()) {
            throw new AccountException(ExceptionCode.ACCOUNT_NOT_FOUND);
        }

        return accountOptional.get();
    }



//    @RequestMapping(value = "/{tenantId}", method = RequestMethod.POST)
//    public ResponseEnvelope createAccount(
//            @RequestBody AccountModel account,
//            @PathVariable("tenantId")  String tenantId
//    ) throws AccountException {
//        if (!RequestValidation.validateCreateAccount(tenantId, account)) {
//            throw new AccountException(ExceptionCode.BAD_REQUEST);
//        }
//
//        Optional<AccountModel> accountOptional;
//        List<ErrorModel> errors = new ArrayList<>();
//
//        try {
//            accountOptional = accountFacade.createAccount(tenantId, account.getName(), account.getEmail(), account.getPassword() );
//        } catch (AccountException aex) {
//            log.error("", aex);
//            errors.add(new ErrorModel(aex.getCode().name(), aex.getMessage()));
//            accountOptional = Optional.empty();
//        } catch (Exception ex) {
//            //todo: move out all the Exceptions
//            log.error("", ex);
//            return null;
//        }
//
//        if (!accountOptional.isPresent() && errors.isEmpty()) {
//            errors.add(new ErrorModel(ExceptionCode.UNKNOWN.name(), ExceptionCode.UNKNOWN.getMessage()));
//        }
//
//        return new ResponseEnvelope.Builder<AccountModel>()
//                .withData(accountOptional.orElse(null))
//                .withErrors(errors)
//                .build();
//    }

//    @RequestMapping(value = "/{tenantId}/{accountId}", method = RequestMethod.POST)
//    public ResponseEnvelope updateAccount(
//            AccountModel account,
//            @PathVariable("tenantId") String tenantId,
//            @PathVariable("accountId") long accountId
//    ) throws AccountException {
//        if (!RequestValidation.validateUpdateAccount(tenantId, accountId, account)) {
//            throw new AccountException(ExceptionCode.BAD_REQUEST);
//        }
//
//        Optional<AccountModel> accountOptional;
//        List<ErrorModel> errors = new ArrayList<>();
//
//        try {
//            accountOptional = accountFacade.updateAccount(tenantId, accountId, account);
//        } catch (AccountException aex) {
//            log.error("", aex);
//            errors.add(new ErrorModel(aex.getCode().name(), aex.getMessage()));
//            accountOptional = Optional.empty();
//        } catch (Exception ex) {
//            //todo: move out all the Exceptions
//            log.error("", ex);
//            return null;
//        }
//
//        if (!accountOptional.isPresent() && errors.isEmpty()) {
//            errors.add(new ErrorModel(ExceptionCode.UNKNOWN.name(), ExceptionCode.UNKNOWN.getMessage()));
//        }
//
//        return new ResponseEnvelope.Builder<AccountModel>()
//                .withData(accountOptional.orElse(null))
//                .withErrors(errors)
//                .build();
//    }
}
