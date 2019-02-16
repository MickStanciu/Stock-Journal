package com.example.timesheet.api.service;

import com.example.timesheet.api.model.TimeSheetEntryModel;
import com.example.timesheet.api.repository.TimeSheetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TimeSheetService {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetService.class);

    private TimeSheetRepository timeSheetRepository;

    @Autowired
    public TimeSheetService(TimeSheetRepository timeSheetRepository) {
        this.timeSheetRepository = timeSheetRepository;
    }

    public List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, long accountId, LocalDateTime from, LocalDateTime to) {
        return timeSheetRepository.getEntriesByIdAndTime(tenantId, accountId, from, to);
    }

}
