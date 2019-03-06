package com.example.gateway.api.gateway;

import com.example.account.api.spec.model.AccountModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "account-api", url = "localhost:8082/api/v1")
public interface AccountApiProxy {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<AccountModel> accountByEmailAndPassword(
            @RequestParam(name = "email", defaultValue = "")  String email,
            @RequestParam(name = "password", defaultValue = "") String password
    );


    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    ResponseEntity<AccountModel> accountById(
            @PathVariable("accountId") String accountId
    );

//    @RequestMapping(value = "/relations/{tenantId}/{parentId}", method = RequestMethod.GET)
//    ResponseEntity<AccountModel> getAccountByRelationship(
//            @PathVariable("tenantId") String tenantId,
//            @PathVariable("parentId") long parentId,
//            @RequestParam("depth") Integer depth
//    );
//
//    @RequestMapping(value = "/{tenantId}", method = RequestMethod.POST)
//    ResponseEntity<AccountModel> createAccount(
//            @RequestBody AccountModel account,
//            @PathVariable("tenantId")  String tenantId
//    );
//
//    @RequestMapping(value = "/{tenantId}/{accountId}", method = RequestMethod.POST)
//    ResponseEntity<AccountModel> updateAccount(
//            AccountModel account,
//            @PathVariable("tenantId") String tenantId,
//            @PathVariable("accountId") long accountId
//    );
}
