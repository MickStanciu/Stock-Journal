package com.example.stockdata.api.impl.facade;

import com.example.stockdata.api.impl.calculators.DataProcessorCalculator;
import com.example.stockdata.api.impl.service.HistoryService;
import com.example.stockdata.api.impl.service.StatsService;
import com.example.stockdata.api.spec.model.PriceModel;
import com.example.stockdata.api.spec.model.PriceStatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
//@EnableScheduling
public class DataProcessorFacade {

    private static final Logger log = LoggerFactory.getLogger(DataProcessorFacade.class);
    private static final int BATCH_SIZE = 500;

    private HistoryService historyService;
    private StatsService statsService;
    private DataProcessorCalculator dataProcessorCalculator;

    private boolean statsProcessed = false;


    @Autowired
    public DataProcessorFacade(HistoryService historyService, StatsService statsService, DataProcessorCalculator dataProcessorCalculator) {
        this.historyService = historyService;
        this.statsService = statsService;
        this.dataProcessorCalculator = dataProcessorCalculator;
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 10000) /* 10 sec */
    public void feedProcessor() {
        //todo: implement SYMBOL re-cycling
        log.info("Start processing");
        log.info("  reading unprocessed data ...");
        List<PriceModel> priceModelList = historyService.getPrices("XYZ", BATCH_SIZE);
        dataProcessorCalculator.computePeriodicDailyReturn(priceModelList);
        boolean batchProcessingIsActive;

        if (priceModelList.size() > 0) {
            batchProcessingIsActive = true;
            statsProcessed = false;
            log.info(String.format("  updating ... %d records", priceModelList.size()));
            processBatchData(priceModelList);
        } else {
            batchProcessingIsActive = false;
            processStats();
        }

        if (!batchProcessingIsActive && !statsProcessed) {
            log.info("  updating standard deviation");

            statsProcessed = true;
        }
        log.info("End processing");
    }

    private void processBatchData(List<PriceModel> priceModelList) {
        priceModelList.stream().limit(BATCH_SIZE - 1).forEach(p -> p.setProcessed(true));
        historyService.updatePrices(priceModelList);
    }

    private void processStats() {
        List<PriceModel> priceModelList = historyService.getLastYearPrices("XYZ");
        PriceStatModel model = statsService.getPriceStats("XYZ");

        if (model != null) {
            model.setDate(LocalDate.now());
            model.setSymbol("XYZ");
            model.setStd(dataProcessorCalculator.computeStandardDeviation(priceModelList));
            statsService.updateStats(model);
        } else {
            model = new PriceStatModel();
            model.setDate(LocalDate.now());
            model.setSymbol("XYZ");
            model.setStd(dataProcessorCalculator.computeStandardDeviation(priceModelList));
            statsService.insertStats(model);
        }
    }
}
