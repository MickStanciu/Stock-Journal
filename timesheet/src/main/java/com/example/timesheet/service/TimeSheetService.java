package com.example.timesheet.service;

import com.example.timesheet.model.TimeSheetEntryModel;
import com.example.timesheet.repository.TimeSheetDao;
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

    @Autowired
    public TimeSheetService(TimeSheetDao timeSheetDao) {
        this.timeSheetDao = timeSheetDao;
    }

    public List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, BigInteger accountId, LocalDateTime from, LocalDateTime to) {
        return timeSheetDao.getEntriesByIdAndTime(tenantId, accountId, from, to);
    }

}
