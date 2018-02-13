//package com.example.tenant.rest;
//
//import com.example.common.rest.envelope.ResponseEnvelope;
//import com.example.tenant.model.Tenant;
//import com.example.tenant.service.TenantService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import javax.ws.rs.core.Response;
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TenantRestTest {
//
//    @Mock
//    private TenantService tenantService;
//
//    @InjectMocks
//    private final TenantRest tenantRest = new TenantRest();
//
//    @Test
//    public void testGetTenant() {
//        Tenant tenantFixture = new Tenant("id", "name");
//        when(tenantService.getTenant("id")).thenReturn(Optional.of(tenantFixture));
//
//        Response response = tenantRest.getTenant("id");
//        ResponseEnvelope responseEnvelope = (ResponseEnvelope) response.getEntity();
//        Tenant item = (Tenant) responseEnvelope.getData();
//
//        assertEquals("Id should be equal to: \'id\'", "id", item.getId());
//    }
//}
