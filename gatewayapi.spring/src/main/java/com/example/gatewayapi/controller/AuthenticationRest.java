package com.example.gatewayapi.controller;

import com.example.common.rest.dto.ErrorDto;
import com.example.common.rest.envelope.ResponseEnvelope;
import com.example.gatewayapi.exception.ExceptionCode;
import com.example.gatewayapi.exception.GatewayApiException;
import com.example.gatewayapi.model.AuthTokenModel;
import com.example.gatewayapi.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationRest {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationRest.class);

    private AuthenticationService authService;

    @Autowired
    public AuthenticationRest(AuthenticationService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "/{tenantId}")
    public ResponseEntity authenticate(
            @PathVariable("tenantId") String tenantId,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        //todo validate input

        List<ErrorDto> errors = new ArrayList<>();
        HttpStatus responseStatus = HttpStatus.OK;

        AuthTokenModel response = null;
        try {
            response = authService.getAuthResponse(tenantId, email, password);
        } catch (GatewayApiException e) {
            errors.add(new ErrorDto(ExceptionCode.REQUEST_NOT_AUTHORIZED.name(), ExceptionCode.REQUEST_NOT_AUTHORIZED.getMessage()));
            responseStatus = HttpStatus.UNAUTHORIZED;
        }

        ResponseEnvelope responseEnvelope = new ResponseEnvelope.Builder<AuthTokenModel>()
                .withData(response)
                .withErrors(errors)
                .build();

        return ResponseEntity
                .status(responseStatus)
                .body(responseEnvelope);
    }
}
