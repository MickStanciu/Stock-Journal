package com.example.tradelog.api.service;

import com.example.tradelog.api.repository.JournalRepository;
import com.example.tradelog.api.spec.model.JournalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalService {

    private static final Logger log = LoggerFactory.getLogger(JournalService.class);

    private JournalRepository journalRepository;

    @Autowired
    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public List<JournalModel> getAllByAccountId(String accountId) {
        return journalRepository.getAllByAccount(accountId);
    }
}
