package com.example.accountapi.service;

import com.example.account.model.AccountModel;
import com.example.account.model.RoleModel;
import com.example.accountapi.repository.AccountRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


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

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCopyAccount() {
        AccountModel newAccount = accountService.copyAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, originalAccountFixture,
                newAccountFixture);
        assertEquals(newAccountFixture.getName(), newAccount.getName(), "Test for name");
        assertEquals(newAccountFixture.getPassword(), newAccount.getPassword(),"Test for password");
        assertEquals(newAccountFixture.getEmail(), newAccount.getEmail(), "Test for email");
        assertEquals(newAccountFixture.isActive(), newAccount.isActive(), "Test for active");
        assertEquals(newAccountFixture.getRole().getId(), newAccount.getRole().getId(),"Test for role ID");
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

        assertEquals(originalAccountFixture.getName(), newAccount.getName(), "Test for name");
        assertEquals(originalAccountFixture.getPassword(), newAccount.getPassword(), "Test for password");
        assertEquals(originalAccountFixture.getEmail(), newAccount.getEmail(), "Test for email");
    }

}
