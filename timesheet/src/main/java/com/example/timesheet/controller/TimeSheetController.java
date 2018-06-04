package com.example.timesheet.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class TimeSheetController {

    private static final Logger log = Logger.getLogger(TimeSheetController.class);

    @RequestMapping("/{tenantId}/{accountId}")
    public ResponseEntity pong(
            @PathVariable(name = "tenantId", value = "0") String tenantId,
            @PathVariable(name = "accountId", value = "0")BigInteger accountId,
            @RequestParam("from") String from,
            @RequestParam("to") String to
            ) {
        log.error("Not implemented");
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }
}
