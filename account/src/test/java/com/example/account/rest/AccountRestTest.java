package com.example.account.rest;

import com.example.account.facade.AccountFacade;
import com.example.account.model.Account;
import com.example.account.model.Role;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountRestTest {

    @Mock
    private AccountFacade accountFacade;

    @InjectMocks
    private final AccountRest accountRest = new AccountRest();

    private final Account accountFixture = new Account.Builder()
            .withId(BigInteger.ONE)
            .withEmail("test@test.com")
            .withName("test.account")
            .withPassword("Password")
            .withRole(new Role(1, "role", "description"))
            .withTenantId("d79ec11a-2011-4423-ba01-3af8de0a3e10")
            .build();

    @Test
    public void testGetAccount() {
        when(accountFacade.getAccount("d79ec11a-2011-4423-ba01-3af8de0a3e10", "test.account", "Password")).thenReturn(Optional.of(accountFixture));

        Response response = accountRest.getAccount("d79ec11a-2011-4423-ba01-3af8de0a3e10", "test.account", "Password");
        assertEquals("Status", 200, response.getStatus());
        ResponseEnvelope responseEnvelope = (ResponseEnvelope) response.getEntity();
        Account item = (Account) responseEnvelope.getData();

        assertEquals("Id should be equal to: \'1\'", BigInteger.ONE, item.getId());
    }
}
