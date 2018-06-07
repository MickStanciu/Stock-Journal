package com.example.timesheet.dao;

import com.example.timesheet.model.TimeSheetEntryModel;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public interface TimeSheetDao {

    List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, BigInteger accountId, LocalDateTime from, LocalDateTime to);
}
