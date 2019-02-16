package com.example.tenant.api.service;

import com.example.tenant.api.repository.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class HealthServiceTest {

    private HealthService service;

    @Mock
    private TenantRepository repository;

    @BeforeEach
    void setup() {
        initMocks(this);
        service = new HealthService(repository);
    }

    @Test
    void isOkTruthyTest() {
        when(repository.checkFirstRecord()).thenReturn(true);
        assertTrue(service.isOk());
        assertTrue(service.getHealth().isFirstRecordOk());
    }

    @Test
    void isOkFalsyTest() {
        when(repository.checkFirstRecord()).thenReturn(false);
        assertFalse(service.isOk());
        assertFalse(service.getHealth().isFirstRecordOk());
    }
}
