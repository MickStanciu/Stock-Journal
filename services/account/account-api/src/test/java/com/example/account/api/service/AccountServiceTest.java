//package com.example.account.api.service;
//
//import com.example.account.api.spec.model.AccountModel;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//
//
//class AccountServiceTest {
//
//    @InjectMocks
//    private AccountService accountService;
//
//    private static final String DEFAULT_ACCOUNT_ID = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
//    private static final String DEFAULT_ACCOUNT_NAME = "name.surname";
//    private static final String DEFAULT_ACCOUNT_PASSWORD = "secret";
//    private static final String DEFAULT_ACCOUNT_EMAIL = "not.set@domain.com";
//
//    private final AccountModel originalAccountFixture = new AccountModel.Builder()
//            .havingPersonalDetails()
//                .withId(DEFAULT_ACCOUNT_ID)
//                .withEmail(DEFAULT_ACCOUNT_EMAIL)
//                .withName(DEFAULT_ACCOUNT_NAME)
//                .withPassword(DEFAULT_ACCOUNT_PASSWORD)
//                .withFlagActive(true)
//            .build();
//
//    private final AccountModel newAccountFixture = new AccountModel.Builder()
//            .havingPersonalDetails()
//                .withId(DEFAULT_ACCOUNT_ID)
//                .withEmail("lola.white@domain.com")
//                .withName("lola.white")
//                .withPassword("123456")
//                .withFlagActive(false)
//            .build();
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void testCopyAccount() {
//        AccountModel newAccount = accountService.copyAccount(DEFAULT_ACCOUNT_ID, originalAccountFixture, newAccountFixture);
//        Assertions.assertEquals(newAccountFixture.getName(), newAccount.getName(), "Test for name");
//        Assertions.assertEquals(newAccountFixture.getPassword(), newAccount.getPassword(), "Test for password");
//        Assertions.assertEquals(newAccountFixture.getEmail(), newAccount.getEmail(), "Test for email");
//        Assertions.assertEquals(newAccountFixture.isActive(), newAccount.isActive(), "Test for active");
//    }
//
//    @Test
//    void testCopyAccountNulls() {
//        AccountModel newAccountFixture = new AccountModel.Builder()
//            .havingPersonalDetails()
//                .withId(DEFAULT_ACCOUNT_ID)
//                .withFlagActive(true)
//            .build();
//
//        AccountModel newAccount = accountService.copyAccount(DEFAULT_ACCOUNT_ID, originalAccountFixture,
//                newAccountFixture);
//
//        Assertions.assertEquals(originalAccountFixture.getName(), newAccount.getName(), "Test for name");
//        Assertions.assertEquals(originalAccountFixture.getPassword(), newAccount.getPassword(), "Test for password");
//        Assertions.assertEquals(originalAccountFixture.getEmail(), newAccount.getEmail(), "Test for email");
//        Assertions.assertEquals(originalAccountFixture.getEmail(), newAccount.getEmail(), "Test for email");
//    }
//
//}
