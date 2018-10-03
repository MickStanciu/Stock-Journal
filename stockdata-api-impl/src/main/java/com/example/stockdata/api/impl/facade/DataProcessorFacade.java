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
        List<PriceModel> priceModelList = historyService.getPricesForSymbol("IWM");
        dataProcessorCalculator.computePeriodicDailyReturn(priceModelList);
        if (priceModelList.size() > 0) {
            log.info(String.format("  updating ... %d records", priceModelList.size()));
            historyService.updatePrices(priceModelList);
        }
        log.info("End processing");
    }


}
