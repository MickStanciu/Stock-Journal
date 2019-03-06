package com.example.gateway.api.gateway;

import com.example.tradelog.api.spec.model.JournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TradeLogGateway {

    private static final Logger log = LoggerFactory.getLogger(TradeLogGateway.class);

    private TradeLogApiProxy tradeLogApiProxy;

    public List<JournalModel> getAllByAccountId(String accountId) {
        return getFake();
    }


    private List<JournalModel> getFake() {
        return Collections.emptyList();
    }
}
