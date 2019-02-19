package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.JournalRepository;
import com.example.tradelog.api.spec.model.HealthModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    private static final Logger log = LoggerFactory.getLogger(HealthService.class);

    private final HealthModel healthModel;

    private JournalRepository journalRepository;

    @Autowired
    public HealthService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
        healthModel = new HealthModel();
        healthModel.setFirstRecordOk(false);
    }

    private void isOk() {
        boolean firstRecordOk;
        try {
            firstRecordOk = journalRepository.checkFirstRecord();
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
