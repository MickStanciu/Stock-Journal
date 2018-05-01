package com.example.account.service;

import com.example.account.dao.AccountDao;
import com.example.account.model.Account;
import com.example.account.model.Role;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigInteger;

import static org.testng.Assert.assertEquals;


public class AccountServiceTest {

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private final AccountService accountService = new AccountService();

    private static String DEFAULT_TENANT_ID = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
    private static BigInteger DEFAULT_ACCOUNT_ID = BigInteger.ONE;
    private static String DEFAULT_ACCOUNT_NAME = "name.surname";
    private static String DEFAULT_ACCOUNT_PASSWORD = "secret";
    private static String DEFAULT_ACCOUNT_EMAIL = "not.set@domain.com";

    private final Account originalAccountFixture = new Account.Builder()
            .havingPersonalDetails()
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ACCOUNT_ID)
                .withEmail(DEFAULT_ACCOUNT_EMAIL)
                .withName(DEFAULT_ACCOUNT_NAME)
                .withPassword(DEFAULT_ACCOUNT_PASSWORD)
                .withFlagActive(true)
            .havingRole()
                .withRole(new Role(5, "role"))
            .build();

    private final Account newAccountFixture = new Account.Builder()
            .havingPersonalDetails()
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ACCOUNT_ID)
                .withEmail("lola.white@domain.com")
                .withName("lola.white")
                .withPassword("123456")
                .withFlagActive(false)
            .havingRole()
                .withRole(new Role(4, "role"))
            .build();

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCopyAccount() {
        Account newAccount = accountService.copyAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, originalAccountFixture,
                newAccountFixture);
        assertEquals(newAccountFixture.getName(), newAccount.getName(), "Test for name");
        assertEquals(newAccountFixture.getPassword(), newAccount.getPassword(),"Test for password");
        assertEquals(newAccountFixture.getEmail(), newAccount.getEmail(), "Test for email");
        assertEquals(newAccountFixture.isActive(), newAccount.isActive(), "Test for active");
        assertEquals(newAccountFixture.getRole().getId(), newAccount.getRole().getId(),"Test for role ID");
    }

    @Test
    public void testCopyAccountNulls() {
        Account newAccountFixture = new Account.Builder()
            .havingPersonalDetails()
                .withTenantId(DEFAULT_TENANT_ID)
                .withId(DEFAULT_ACCOUNT_ID)
                .withFlagActive(true)
            .havingRole()
                .withRole(new Role(5, "role"))
            .build();

        Account newAccount = accountService.copyAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, originalAccountFixture,
                newAccountFixture);

        assertEquals(originalAccountFixture.getName(), newAccount.getName(), "Test for name");
        assertEquals(originalAccountFixture.getPassword(), newAccount.getPassword(), "Test for password");
        assertEquals(originalAccountFixture.getEmail(), newAccount.getEmail(), "Test for email");
    }

}
