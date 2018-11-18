//package com.example.tenant.api.rest;
//
//import com.example.common.rest.dto.ErrorDto;
//import com.example.common.rest.envelope.ResponseEnvelope;
//import com.example.tenant.model.TenantModel;
//import com.example.tenant.api.exception.ExceptionCode;
//import com.example.tenant.api.service.TenantService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.inject.Inject;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Path("/v1")
//@Produces(MediaType.APPLICATION_JSON)
//public class TenantResource {
//
//    private static final Logger log = LoggerFactory.getLogger(TenantResource.class);
//
//    @Inject
//    private TenantService tenantService;
//
//    @GET
//    @Path("/{tenantId}")
//    public Response tenantByUUID(@PathParam("tenantId") @DefaultValue("0") String tenantId) {
//        //todo: better check, use FieldValidator
//        if (tenantId.length() == 0 || tenantId.equals("0")) {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//
//        List<ErrorDto> errors = new ArrayList<>();
//
//        //todo: catch all errors
//        Optional<TenantModel> tenantModelOptional;
//        try {
//            tenantModelOptional = tenantService.getTenant(tenantId);
//        } catch (Exception ex) {
//            log.error("", ex);
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//        }
//
//
//        if (!tenantModelOptional.isPresent()) {
//            errors.add(new ErrorDto(ExceptionCode.TENANT_NOT_FOUND.name(), ExceptionCode.TENANT_NOT_FOUND.getMessage()));
//        }
//
//        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<TenantModel>()
//                .withData(tenantModelOptional.orElse(null))
//                .withErrors(errors)
//                .build();
//
//        return Response.status(Response.Status.OK)
//                .entity(responseEnvelope)
//                .build();
//    }
//}
