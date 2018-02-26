//package com.example.account.service;
//
//import com.example.account.dao.AccountDao;
//import com.example.account.model.Account;
//import com.example.account.model.Role;
//import org.junit.Test;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.math.BigInteger;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AccountServiceTest {
//
//    @Mock
//    private AccountDao accountDao;
//
//    @InjectMocks
//    private final AccountService accountService = new AccountService();
//
//    private static String DEFAULT_TENANT_ID = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
//    private static BigInteger DEFAULT_ACCOUNT_ID = BigInteger.ONE;
//    private static String DEFAULT_ACCOUNT_NAME = "name.surname";
//    private static String DEFAULT_ACCOUNT_PASSWORD = "secret";
//    private static String DEFAULT_ACCOUNT_EMAIL = "not.set@domain.com";
//
//    private final Account originalAccountFixture = new Account.Builder()
//            .withTenantId(DEFAULT_TENANT_ID)
//            .withId(DEFAULT_ACCOUNT_ID)
//            .withEmail(DEFAULT_ACCOUNT_EMAIL)
//            .withName(DEFAULT_ACCOUNT_NAME)
//            .withPassword(DEFAULT_ACCOUNT_PASSWORD)
//            .withRole(new Role(5, "role", "description"))
//            .withFlagActive(true)
//            .build();
//
//    private final Account newAccountFixture = new Account.Builder()
//            .withTenantId(DEFAULT_TENANT_ID)
//            .withId(DEFAULT_ACCOUNT_ID)
//            .withEmail("lola.white@domain.com")
//            .withName("lola.white")
//            .withPassword("123456")
//            .withRole(new Role(4, "role", "description"))
//            .withFlagActive(false)
//            .build();
//
//    @Test
//    public void testCopyAccount() {
//        Account newAccount = accountService.copyAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, originalAccountFixture,
//                newAccountFixture);
//        assertEquals("Test for name", newAccount.getName(), newAccountFixture.getName());
//        assertEquals("Test for password", newAccount.getPassword(), newAccountFixture.getPassword());
//        assertEquals("Test for email", newAccount.getEmail(), newAccountFixture.getEmail());
//        assertEquals("Test for active", newAccount.isActive(), newAccountFixture.isActive());
//        assertEquals("Test for role ID", newAccount.getRole().getId(), newAccountFixture.getRole().getId());
//    }
//
//    @Test
//    public void testCopyAccountNulls() {
//        Account newAccountFixture = new Account.Builder()
//                .withTenantId(DEFAULT_TENANT_ID)
//                .withId(DEFAULT_ACCOUNT_ID)
//                .withRole(new Role(5, "role", "description"))
//                .withFlagActive(true)
//                .build();
//
//        Account newAccount = accountService.copyAccount(DEFAULT_TENANT_ID, DEFAULT_ACCOUNT_ID, originalAccountFixture,
//                newAccountFixture);
//
//        assertEquals("Test for name", newAccount.getName(), originalAccountFixture.getName());
//        assertEquals("Test for password", newAccount.getPassword(), originalAccountFixture.getPassword());
//        assertEquals("Test for email", newAccount.getEmail(), originalAccountFixture.getEmail());
//    }
//
//}
