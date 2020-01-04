//package com.example.gateway.api.controller;
//
//import com.example.gateway.api.exception.ExceptionCode;
//import com.example.gateway.api.exception.GatewayApiException;
//import com.example.gateway.api.service.AuthenticationService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//
//@RestController
//@RequestMapping(value = "/api/v1/auth", produces = "application/json")
//public class AuthenticationResource {
//
//    private static final Logger log = LoggerFactory.getLogger(AuthenticationResource.class);
//
//    private AuthenticationService authService;
//
//    public AuthenticationResource(AuthenticationService authService) {
//        this.authService = authService;
//    }
//
//    @RequestMapping(value = "/{tenantId}", method = RequestMethod.GET)
//    public ResponseEntity<AuthTokenModel> authenticate (
//            @RequestParam("email") String email,
//            @RequestParam("password") String password
//    ) throws GatewayApiException {
//        //todo validate input
//
//        Optional<AuthTokenModel> authOptional = authService.getAuthResponse(email, password);
//
//        if (authOptional.isEmpty()) {
//            throw new GatewayApiException(ExceptionCode.REQUEST_NOT_AUTHORIZED);
//        }
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(authOptional.get());
//    }
//}
