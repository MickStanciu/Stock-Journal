package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.OptionsJournalRepository;
import com.example.tradelog.api.spec.model.HealthModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    private static final Logger log = LoggerFactory.getLogger(HealthService.class);

    private final HealthModel healthModel;

    private OptionsJournalRepository optionsJournalRepository;

    public HealthService(OptionsJournalRepository optionsJournalRepository) {
        this.optionsJournalRepository = optionsJournalRepository;
        healthModel = new HealthModel();
        healthModel.setFirstRecordOk(false);
    }

    private void isOk() {
        boolean firstRecordOk;
        try {
            firstRecordOk = optionsJournalRepository.checkFirstRecord();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            firstRecordOk = false;
        }
        healthModel.setFirstRecordOk(firstRecordOk);
    }

    public HealthModel getHealthModel() {
        isOk();
        return healthModel;
    }
}
