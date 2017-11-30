package com.example.account.rest;

import com.example.account.model.Account;
import com.example.account.service.AccountService;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Stateless
@Produces("application/json")
public class AccountRest {

    private static final Logger log = Logger.getLogger(AccountRest.class);

    @Inject
    private AccountService accountService;


    @GET
    @Path("/{tenantId}")
    public Response getAccount(
            @PathParam("tenantId") @DefaultValue("0") int tenantId,
            @QueryParam("name") @DefaultValue("") String name,
            @QueryParam("password") @DefaultValue("") String password
    ) {
        if (tenantId == 0 || name.length() == 0 || password.length() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Account account = accountService.getAccount(name, password, tenantId);
        ResponseEnvelope responseEnvelope = new ResponseEnvelope.ResponseEnvelopeBuilder<Account>()
                .data(account)
                .errors(Collections.emptyList())
                .build();

        return Response.status(Response.Status.OK)
                .entity(responseEnvelope)
                .build();
    }
}
