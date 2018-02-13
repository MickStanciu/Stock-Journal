//package com.example.tenant.rest;
//
//import com.example.common.rest.dto.ErrorDto;
//import com.example.common.rest.envelope.ResponseEnvelope;
//import com.example.tenant.exception.ExceptionCode;
//import com.example.tenant.model.Tenant;
//import com.example.tenant.service.TenantService;
//import org.apache.log4j.Logger;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.ws.rs.DefaultValue;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Response;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Stateless
//@Path("/")
//@Produces("application/json")
//public class TenantRest {
//
//    private static final Logger log = Logger.getLogger(TenantRest.class);
//
//    @Inject
//    private TenantService tenantService;
//
//    @GET
//    @Path("/{tenantId}")
//    public Response getTenant(@PathParam("tenantId") @DefaultValue("0") String tenantId) {
//        //todo: better check
//        if (tenantId.length() == 0 || tenantId.equals("0")) {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//
//        List<ErrorDto> errors = new ArrayList<>();
//
//        //todo: catch all errors
//        Optional<Tenant> tenantOptional;
//        try {
//            tenantOptional = tenantService.getTenant(tenantId);
//        } catch (Exception ex) {
//            log.error(ex);
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//        }
//
//        Tenant tenant = null;
//        if (!tenantOptional.isPresent()) {
//            errors.add(new ErrorDto(ExceptionCode.TENANT_NOT_FOUND.name(), ExceptionCode.TENANT_NOT_FOUND.getMessage()));
//        } else {
//            tenant = tenantOptional.get();
//        }
//
//        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<Tenant>()
//                .withData(tenant)
//                .withErrors(errors)
//                .build();
//
//        return Response.status(Response.Status.OK)
//                .entity(responseEnvelope)
//                .build();
//    }
//}
