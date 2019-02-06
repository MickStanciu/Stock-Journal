package com.example.tenant.api.service;

import com.example.tenant.api.repository.TenantRepository;
import com.example.tenant.api.spec.model.TenantModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class TenantServiceTest {

    private TenantService service;

    @Mock
    private TenantRepository repository;

    @BeforeEach
    void setup() {
        initMocks(this);
        service = new TenantService(repository);
    }

    @Test
    void getTenantTest() {
        String uuid = "123-123-123";
        TenantModel model = new TenantModel(uuid, "NAME");
        when(repository.getTenant(anyString())).thenReturn(model);
        assertTrue(service.getTenant(uuid).isPresent());
    }

    @Test
    void getTenantFailedTest() {
        String uuid = "123-123-123";
        when(repository.getTenant(anyString())).thenThrow(DuplicateKeyException.class);
        assertFalse(service.getTenant(uuid).isPresent());
    }
}
