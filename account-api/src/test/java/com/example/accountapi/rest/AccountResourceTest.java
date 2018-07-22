package com.example.accountapi.rest;

import com.example.account.model.AccountModel;
import com.example.account.model.RoleModel;
import com.example.accountapi.facade.AccountFacade;
import com.example.common.rest.envelope.ResponseEnvelope;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;


public class AccountResourceTest {

    @Mock
    private AccountFacade accountFacade;

    @InjectMocks
    private AccountResource accountResource;

    private static final String DEFAULT_TENANT_ID = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
    private static final long DEFAULT_ACCOUNT_ID = 1L;
    private static final String DEFAULT_ACCOUNT_NAME = "name.surname";
    private static final String DEFAULT_ACCOUNT_PASSWORD = "secret";
    private static final String DEFAULT_ACCOUNT_EMAIL = "not.set@domain.com";

    private final AccountModel accountFixture = AccountModel.builder()
            .havingPersonalDetails()
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ACCOUNT_ID)
                .withEmail(DEFAULT_ACCOUNT_EMAIL)
                .withName(DEFAULT_ACCOUNT_NAME)
                .withPassword(DEFAULT_ACCOUNT_PASSWORD)
                .withFlagActive(true)
            .havingRole()
                .withRole(new RoleModel(2, "role"))
            .build();

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAccount() {
        when(accountFacade.getAccount(DEFAULT_TENANT_ID, "test.account", "Password"))
                .thenReturn(Optional.of(accountFixture));

        Response response = accountResource.accountByEmailAndPassword(DEFAULT_TENANT_ID, "test.account", "Password");
        assertEquals(200, response.getStatus(), "Status");
        ResponseEnvelope responseEnvelope = (ResponseEnvelope) response.getEntity();
        AccountModel item = (AccountModel) responseEnvelope.getData();

        assertEquals(1L, item.getId(), "Id should be equal to: \'1\'");
    }
}
