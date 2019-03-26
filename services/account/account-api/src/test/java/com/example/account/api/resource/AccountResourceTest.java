package com.example.account.api.resource;

import com.example.account.api.exception.AccountException;
import com.example.account.api.facade.AccountFacade;
import com.example.account.api.spec.model.AccountModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;


class AccountResourceTest {

    @Mock
    private AccountFacade accountFacade;

    @InjectMocks
    private AccountResource accountResource;

    private static final String DEFAULT_ACCOUNT_ID = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
    private static final String DEFAULT_ACCOUNT_NAME = "name.surname";
    private static final String DEFAULT_ACCOUNT_PASSWORD = "secret";
    private static final String DEFAULT_ACCOUNT_EMAIL = "not.set@domain.com";

    private final AccountModel accountFixture = AccountModel.builder()
            .havingPersonalDetails()
                .withId(DEFAULT_ACCOUNT_ID)
                .withEmail(DEFAULT_ACCOUNT_EMAIL)
                .withName(DEFAULT_ACCOUNT_NAME)
                .withPassword(DEFAULT_ACCOUNT_PASSWORD)
                .withFlagActive(true)
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAccount() throws AccountException {
        Mockito.when(accountFacade.getAccount("test.account", "Password"))
                .thenReturn(Optional.of(accountFixture));

        AccountModel item = accountResource.accountByEmailAndPassword("test.account", "Password");

        assert item != null;
        Assertions.assertEquals(DEFAULT_ACCOUNT_ID, item.getId(), String.format("Id should be equal to: %s", DEFAULT_ACCOUNT_ID));
    }
}
