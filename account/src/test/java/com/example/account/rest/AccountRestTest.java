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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountRestTest {

    @Mock
    private AccountFacade accountFacade;

    @InjectMocks
    private final AccountRest accountRest = new AccountRest();

    @Test
    public void testGetAccount() {
        Account accountFixture = new Account.Builder()
                .withId(BigInteger.ONE)
                .withEmail("test@test.com")
                .withName("test.account")
                .withPassword("Password")
                .withRole(new Role(1, "role", "description"))
                .withTenantId("1")
                .build();

        when(accountFacade.getAccount("test.account", "Password", "1")).thenReturn(Optional.of(accountFixture));

        Response response = accountRest.getAccount("1", "test.account", "Password");
        ResponseEnvelope responseEnvelope = (ResponseEnvelope) response.getEntity();
        Account item = (Account) responseEnvelope.getData();

        assertEquals("Id should be equal to: \'1\'", BigInteger.ONE, item.getId());
    }
}
