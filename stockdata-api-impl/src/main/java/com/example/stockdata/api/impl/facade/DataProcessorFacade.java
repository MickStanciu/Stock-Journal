package com.example.stockdata.api.impl.facade;

import com.example.stockdata.api.impl.calculators.DataProcessorCalculator;
import com.example.stockdata.api.impl.service.HistoryService;
import com.example.stockdata.api.spec.model.PriceModel;
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
    private static final int BATCH_SIZE = 500;
    private HistoryService historyService;
    private DataProcessorCalculator dataProcessorCalculator;


    @Autowired
    public DataProcessorFacade(HistoryService historyService, DataProcessorCalculator dataProcessorCalculator) {
        this.historyService = historyService;
        this.dataProcessorCalculator = dataProcessorCalculator;
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 10000) /* 10 sec */
    public void feedProcessor() {
        log.info("Start processing");
        log.info("  reading unprocessed data ...");
        List<PriceModel> priceModelList = historyService.getPrices(BATCH_SIZE);
        dataProcessorCalculator.computePeriodicDailyReturn(priceModelList);
        if (priceModelList.size() > 0) {
            log.info(String.format("  updating ... %d records", priceModelList.size()));
            priceModelList.stream().limit(BATCH_SIZE - 1).forEach(p -> p.setProcessed(true));
            historyService.updatePrices(priceModelList);
        }
        log.info("End processing");
    }


}
