package com.example.timesheet.service;

import com.example.timesheet.dao.TimeSheetDao;
import com.example.timesheet.dao.TimeSheetDaoImpl;
import com.example.timesheet.model.TimeSheetEntryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeSheetService {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetService.class);

    private TimeSheetDao timeSheetDao;

    //todo: qualifier
    @Autowired
    public TimeSheetService(TimeSheetDaoImpl timeSheetRepository) {
        this.timeSheetDao = timeSheetRepository;
    }

    public List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, BigInteger accountId, LocalDateTime from, LocalDateTime to) {
        return timeSheetDao.getEntriesByIdAndTime(tenantId, accountId, from, to);
    }

}
