package com.example.timesheet.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class TimeSheetController {

    private static final Logger log = Logger.getLogger(TimeSheetController.class);

    @RequestMapping("/{tenantId}/{accountId}")
    public ResponseEntity pong() {
        log.error("Not implemented");
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }
}
