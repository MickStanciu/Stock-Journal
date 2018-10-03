package com.example.stockdata.api.impl.facade;

import com.example.stockdata.api.impl.service.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@EnableScheduling
public class DataProcessorFacade {

    private static final Logger log = LoggerFactory.getLogger(DataProcessorFacade.class);
    private HistoryService historyService;

    @Autowired
    public DataProcessorFacade(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Scheduled(fixedDelay = 10000) /* 10 sec */
    public void feedProcessor() {
        log.info("Start processing");
        List<Double> historicalData = getHistoricalData();
        System.out.println(historicalData.size());
        log.info("End processing");
    }

    private List<Double> getHistoricalData() {
        String symbol = "IWM";
        return historyService.getHistoryClose(symbol);
    }


}
