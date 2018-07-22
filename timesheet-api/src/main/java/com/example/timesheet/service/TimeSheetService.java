package com.example.timesheet.service;

import com.example.timesheet.model.TimeSheetEntryModel;
import com.example.timesheet.repository.TimeSheetDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class TimeSheetService {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetService.class);

    private TimeSheetDao timeSheetDao;

    @Inject
    public TimeSheetService(TimeSheetDao timeSheetDao) {
        this.timeSheetDao = timeSheetDao;
    }

    public List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, long accountId, LocalDateTime from, LocalDateTime to) {
        return timeSheetDao.getEntriesByIdAndTime(tenantId, accountId, from, to);
    }

}
