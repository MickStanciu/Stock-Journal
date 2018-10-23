package com.example.accountapi.service;

import com.example.accountapi.model.AccountModel;
import com.example.accountapi.model.RoleModel;
import com.example.accountapi.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private static final String DEFAULT_TENANT_ID = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
    private static final long DEFAULT_ACCOUNT_ID = 1L;
    private static final String DEFAULT_ACCOUNT_NAME = "name.surname";
    private static final String DEFAULT_ACCOUNT_PASSWORD = "secret";
    private static final String DEFAULT_ACCOUNT_EMAIL = "not.set@domain.com";

    private final AccountModel originalAccountFixture = new AccountModel.Builder()
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

    private final AccountModel newAccountFixture = new AccountModel.Builder()
            .havingPersonalDetails()
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ACCOUNT_ID)
                .withEmail("lola.white@domain.com")
                .withName("lola.white")
                .withPassword("123456")
                .withFlagActive(false)
            .havingRole()
                .withRole(new RoleModel(4, "role"))
            .build();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCopyAccount() {
        AccountModel newAccount = accountService.copyAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, originalAccountFixture,
                newAccountFixture);
        Assert.assertEquals("Test for name", newAccountFixture.getName(), newAccount.getName());
        Assert.assertEquals("Test for password", newAccountFixture.getPassword(), newAccount.getPassword());
        Assert.assertEquals("Test for email", newAccountFixture.getEmail(), newAccount.getEmail());
        Assert.assertEquals("Test for active", newAccountFixture.isActive(), newAccount.isActive());
        Assert.assertEquals("Test for role ID", newAccountFixture.getRole().getId(), newAccount.getRole().getId());
    }

    @Test
    public void testCopyAccountNulls() {
        AccountModel newAccountFixture = new AccountModel.Builder()
            .havingPersonalDetails()
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ACCOUNT_ID)
                .withFlagActive(true)
            .havingRole()
                .withRole(new RoleModel(2, "role"))
            .build();

        AccountModel newAccount = accountService.copyAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, originalAccountFixture,
                newAccountFixture);

        Assert.assertEquals("Test for name", originalAccountFixture.getName(), newAccount.getName());
        Assert.assertEquals("Test for password", originalAccountFixture.getPassword(), newAccount.getPassword());
        Assert.assertEquals("Test for email", originalAccountFixture.getEmail(), newAccount.getEmail());
    }

}
