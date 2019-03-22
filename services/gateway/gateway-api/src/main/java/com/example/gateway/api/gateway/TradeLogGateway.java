package com.example.gateway.api.gateway;

import com.example.tradelog.api.spec.model.OptionJournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TradeLogGateway {

    private static final Logger log = LoggerFactory.getLogger(TradeLogGateway.class);


    @Autowired
    public TradeLogGateway() {

    }

    public List<OptionJournalModel> getAllByAccountId(String accountId) {
//        ResponseEntity<List<OptionJournalModel>> responseEntity = tradeLogApiProxy.getAllByAccountId(accountId);
//        return responseEntity.getBody();
        return null;
    }

    private List<OptionJournalModel> getFake() {
        return Collections.emptyList();
    }
}
