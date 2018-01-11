package com.example.account.rest;

import com.example.account.facade.AccountFacade;
import com.example.account.model.response.Account;
import com.example.account.model.response.Role;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountRestTest {

    @Mock
    private AccountFacade accountFacade;

    @InjectMocks
    private final AccountRest tenantRest = new AccountRest();

    @Test
    public void testGetAccount() {
        Account accountFixture = new Account.Builder()
                .withId(BigInteger.ONE)
                .withEmail("test@test.com")
                .withName("Test")
                .withPassword("Password")
                .withRole(new Role(1, "role", "description"))
                .withTenantId("1")
                .build();

//        when(tenantService.getTenant("id")).thenReturn(Optional.of(tenantFixture));
//
//        Response response = tenantRest.getTenant("id");
//        ResponseEnvelope responseEnvelope = (ResponseEnvelope) response.getEntity();
//        Tenant item = (Tenant) responseEnvelope.getData();
//
//        assertEquals("Id should be equal to: \'id\'", "id", item.getId());
    }
}
