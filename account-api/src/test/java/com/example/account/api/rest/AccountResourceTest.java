package com.example.account.api.rest;

import com.example.account.api.exception.AccountException;
import com.example.account.api.facade.AccountFacade;
import com.example.account.api.spec.model.AccountModel;
import com.example.account.api.spec.model.RoleModel;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;


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
    public void testGetAccount() throws AccountException {
        Mockito.when(accountFacade.getAccount(DEFAULT_TENANT_ID, "test.account", "Password"))
                .thenReturn(Optional.of(accountFixture));

        ResponseEntity<AccountModel> response = accountResource.accountByEmailAndPassword(DEFAULT_TENANT_ID, "test.account", "Password");
        AccountModel item = response.getBody();

        assert item != null;
        Assert.assertEquals(item.getId(), 1L,"Id should be equal to: \'1\'");
    }
}
