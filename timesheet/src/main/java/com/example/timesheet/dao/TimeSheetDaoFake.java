package com.example.timesheet.dao;

import com.example.common.converter.TimeConversion;
import com.example.timesheet.model.ProjectModel;
import com.example.timesheet.model.TaskModel;
import com.example.timesheet.model.TimeSheetEntryModel;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Repository("TimeSheetDaoFake")
public class TimeSheetDaoFake implements TimeSheetDao {

    @Override
    public List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, BigInteger accountId, LocalDateTime from, LocalDateTime to) {
        ProjectModel projectModel = ProjectModel.builder().withTitle("Project 1").build();
        TaskModel taskModel = TaskModel.builder().withTitle("Task 1").build();

        List<TimeSheetEntryModel> timesheetEntries = new ArrayList<>();

        LocalDateTime begin = TimeConversion.getStartOfDay();

        timesheetEntries.add(
                TimeSheetEntryModel.builder()
                        .fromTime(begin.plusHours(8).toInstant(ZoneOffset.UTC))
                        .toTime(begin.plusHours(10).minusMinutes(1).toInstant(ZoneOffset.UTC))
                        .havingProject(ProjectModel.builder().withTitle("Project A").build())
                        .havingTask(TaskModel.builder().withTitle("Task A1 h8 - h10").build())
                        .withAccountId(BigInteger.ONE)
                        .withTenantId("123")
                        .build());

        timesheetEntries.add(
                TimeSheetEntryModel.builder()
                        .fromTime(begin.plusHours(10).toInstant(ZoneOffset.UTC))
                        .toTime(begin.plusHours(12).minusMinutes(1).toInstant(ZoneOffset.UTC))
                        .havingProject(ProjectModel.builder().withTitle("Project A").build())
                        .havingTask(TaskModel.builder().withTitle("Task A2 h10 - h12").build())
                        .withAccountId(BigInteger.ONE)
                        .withTenantId("123")
                        .build());

        for (int i = 12; i <= 16; i+=1) {
            timesheetEntries.add(
                    TimeSheetEntryModel.builder()
                            .fromTime(from.plusHours(i).toInstant(ZoneOffset.UTC))
                            .toTime(from.plusHours(i).plusMinutes(30 - 1).toInstant(ZoneOffset.UTC))
                            .havingProject(projectModel)
                            .havingTask(TaskModel.builder().withTitle("Task 1 - h" + i).build())
                            .withAccountId(BigInteger.ONE)
                            .withTenantId("123")
                            .build());

            timesheetEntries.add(
                    TimeSheetEntryModel.builder()
                            .fromTime(from.plusHours(i).plusMinutes(30).toInstant(ZoneOffset.UTC))
                            .toTime(from.plusHours(i+1).minusMinutes(1).toInstant(ZoneOffset.UTC))
                            .havingProject(projectModel)
                            .havingTask(TaskModel.builder().withTitle("Task 1 - h" + i).build())
                            .withAccountId(BigInteger.ONE)
                            .withTenantId("123")
                            .build());
        }

        timesheetEntries.add(
                TimeSheetEntryModel.builder()
                        .fromTime(from.plusHours(17).plusMinutes(30).toInstant(ZoneOffset.UTC))
                        .toTime(from.plusHours(17).plusMinutes(59).toInstant(ZoneOffset.UTC))
                        .havingProject(ProjectModel.builder().withTitle("Slot 35").build())
                        .havingTask(TaskModel.builder().withTitle("Task 1 - h17:30").build())
                        .withAccountId(BigInteger.ONE)
                        .withTenantId("123")
                        .build());

        return timesheetEntries;
    }
}
