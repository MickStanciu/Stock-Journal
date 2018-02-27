package com.example.tenant.rest;

import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.tenant.model.Tenant;
import com.example.tenant.service.TenantService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class TenantRestTest {

    @Mock
    private TenantService tenantService;

    @InjectMocks
    private final TenantRest tenantRest = new TenantRest();

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTenant() {
        Tenant tenantFixture = new Tenant("id", "name");
        when(tenantService.getTenant("id")).thenReturn(Optional.of(tenantFixture));

        Response response = tenantRest.getTenant("id");
        ResponseEnvelope responseEnvelope = (ResponseEnvelope) response.getEntity();
        Tenant item = (Tenant) responseEnvelope.getData();

        assertEquals("id", item.getId(), "Id should be equal to: \'id\'");
    }
}
