package com.example.gateway.api.gateway;

import com.example.account.api.spec.model.AccountModel;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.login.AccountException;

@FeignClient(name = "account-api", url = "localhost:8082")
public interface AccountApiProxy {

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.GET)
    ResponseEntity<AccountModel> accountByEmailAndPassword(
            @PathVariable(name = "tenantId") String tenantId,
            @RequestParam(name = "email", defaultValue = "")  String email,
            @RequestParam(name = "password", defaultValue = "") String password
    ) throws AccountException;


    @RequestMapping(value = "/{tenantId}/{accountId}", method = RequestMethod.GET)
    ResponseEnvelope accountById(
            @PathVariable("tenantId") String tenantId,
            @PathVariable("accountId") long accountId
    ) throws AccountException;

    @RequestMapping(value = "/relations/{tenantId}/{parentId}", method = RequestMethod.GET)
    public ResponseEnvelope getAccountByRelationship(
            @PathVariable("tenantId") String tenantId,
            @PathVariable("parentId") long parentId,
            @RequestParam("depth") Integer depth
    ) throws AccountException;

    @RequestMapping(value = "/{tenantId}", method = RequestMethod.POST)
    public ResponseEnvelope createAccount(
            @RequestBody AccountModel account,
            @PathVariable("tenantId")  String tenantId
    ) throws AccountException;

    @RequestMapping(value = "/{tenantId}/{accountId}", method = RequestMethod.POST)
    public ResponseEnvelope updateAccount(
            AccountModel account,
            @PathVariable("tenantId") String tenantId,
            @PathVariable("accountId") long accountId
    ) throws AccountException;
}
