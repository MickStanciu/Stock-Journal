package com.example.gateway.api.gateway;

import com.example.account.api.spec.model.AccountModel;
import com.example.account.api.spec.model.RoleInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountGateway {

    private static final Logger log = LoggerFactory.getLogger(AccountGateway.class);

    private AccountApiProxy accountApiProxy;

    @Autowired
    public AccountGateway(AccountApiProxy accountApiProxy) {
        this.accountApiProxy = accountApiProxy;
    }

    public Optional<AccountModel> getAccount(String accountId) {
        ResponseEntity<AccountModel> responseEntity = accountApiProxy.accountById(accountId);
        return Optional.ofNullable(responseEntity.getBody());
    }

    public Optional<AccountModel> getAccount(String email, String password) {
        ResponseEntity<AccountModel> responseEntity = accountApiProxy.accountByEmailAndPassword(email, password);
        return Optional.ofNullable(responseEntity.getBody());
    }

//    private Optional<AccountModel> getAccountModel(URI uri) {
//        WebTarget target = this.getTarget(uri);
//
//        ResponseEnvelope<AccountModel> envelope;
//
//        try {
//            Response response = target
//                    .request(MediaType.APPLICATION_JSON)
//                    .get(Response.class);
//            envelope = response.readEntity(new GenericType<ResponseEnvelope<AccountModel>>() {});
//            response.close();
//        }  catch (Exception ex) {
//            log.error("Hmmm", ex);
//            return Optional.empty();
//        }
//
//        if (envelope.getErrors() != null) {
//            processErrors(envelope.getErrors());
//        }
//
//        return Optional.of(envelope.getData());
//    }

    private Optional<AccountModel> getFake() {
        Set<RoleInfoModel> roles = new HashSet<>();
        roles.add(RoleInfoModel.LOG_IN);
        roles.add(RoleInfoModel.CREATE_ACCOUNT);

        AccountModel model = AccountModel.builder()
                .havingPersonalDetails()
                .withFlagActive(true)
                .withEmail("test@example.com")
                .withName("Mick")
                .withPassword("123")
                .withId("FAKE")
                .build();
        return Optional.of(model);
    }


}
