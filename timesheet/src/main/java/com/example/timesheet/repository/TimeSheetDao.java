package com.example.timesheet.repository;

import com.example.timesheet.model.TimeSheetEntryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
public class TimeSheetDao {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetDao.class);

    public List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, BigInteger accountId, LocalDateTime from, LocalDateTime to) {
        return Collections.emptyList();
    }
}
