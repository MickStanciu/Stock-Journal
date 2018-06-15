package com.example.tenant.controller;

import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.tenant.model.TenantModel;
import com.example.tenant.service.TenantService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class TenantControllerTest {

    @Mock
    private TenantService tenantService;

    @InjectMocks
    private final TenantController tenantController = new TenantController(tenantService);

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTenant() {
        TenantModel tenantFixture = new TenantModel("id", "name");
        when(tenantService.getTenant("id")).thenReturn(tenantFixture);

        Response response = tenantController.tenantByUUID("id");
        ResponseEnvelope responseEnvelope = (ResponseEnvelope) response.getEntity();
        TenantModel item = (TenantModel) responseEnvelope.getData();

        assertEquals("id", item.getId(), "Id should be equal to: \'id\'");
    }
}
