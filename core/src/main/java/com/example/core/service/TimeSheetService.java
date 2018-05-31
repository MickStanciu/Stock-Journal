package com.example.core.service;

import com.example.core.dao.TimesheetDao;
import com.example.core.model.TimeSheetEntryModel;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class TimeSheetService {

    private static final Logger log = Logger.getLogger(TimeSheetService.class);

    @Inject
    private TimesheetDao timesheetDao;

    public List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, BigInteger accountId, LocalDateTime from, LocalDateTime to) {
        return timesheetDao.getEntriesByIdAndTime(tenantId, accountId, from, to);
    }
}