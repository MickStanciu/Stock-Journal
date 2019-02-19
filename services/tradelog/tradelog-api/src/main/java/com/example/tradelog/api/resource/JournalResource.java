package com.example.tradelog.api.resource;

import com.example.tradelog.api.exception.TradeLogException;
import com.example.tradelog.api.service.JournalService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.spec.model.JournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class JournalResource {

    private static final Logger log = LoggerFactory.getLogger(JournalResource.class);

    private JournalService journalService;

    @Autowired
    public JournalResource(JournalService journalService) {
        this.journalService = journalService;
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<List<JournalModel>> getAllByAccountId(@PathVariable("accountId") String accountId) throws TradeLogException {
        if (!RequestValidation.validateGetAllByAccountId(accountId)) {
            throw new TradeLogException(ExceptionCode.BAD_REQUEST);
        }

        List<JournalModel> tradeLogs = journalService.getAllByAccountId(accountId);

        if (tradeLogs.isEmpty()) {
            throw new TradeLogException(ExceptionCode.TRADELOG_EMPTY);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tradeLogs);
    }
}
