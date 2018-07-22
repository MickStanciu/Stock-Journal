package com.example.timesheet.repository;

import com.example.timesheet.model.TimeSheetEntryModel;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeSheetDao {

    List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, long accountId, LocalDateTime from, LocalDateTime to);
}
