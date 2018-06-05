package com.example.timesheet.model;

import com.example.timesheet.statemachine.State;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;

public class TimeSheetEntryModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigInteger id;
    private Instant fromTime;
    private Instant toTime;
    private String title;
    private State state;

    private String tenantId;
    private BigInteger accountId;

    private ProjectModel project;
    private TaskModel task;

    private TimeSheetEntryModel() {
        //required by Jackson
    }

    public BigInteger getId() {
        return id;
    }

    public Instant getFromTime() {
        return fromTime;
    }

    public Instant getToTime() {
        return toTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public State getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public BigInteger getAccountId() {
        return accountId;
    }

    public ProjectModel getProject() {
        return project;
    }

    public TaskModel getTask() {
        return task;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "TimesheetEntry{" +
                "accountId=" + accountId +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                '}';
    }

    public static class Builder {
        final TimeSheetEntryModel timesheetEntry;

        Builder() {
            timesheetEntry = new TimeSheetEntryModel();
        }

        public Builder(TimeSheetEntryModel timesheetEntry) {
            this.timesheetEntry = timesheetEntry;
        }

        public Builder withTenantId(String tenantId) {
            timesheetEntry.tenantId = tenantId;
            return this;
        }

        public Builder withAccountId(BigInteger accountId) {
            timesheetEntry.accountId = accountId;
            return this;
        }

        public Builder fromTime(Instant from) {
            timesheetEntry.fromTime = from;
            return this;
        }

        public Builder toTime(Instant to) {
            timesheetEntry.toTime = to;
            return this;
        }

        public Builder havingProject(ProjectModel project) {
            timesheetEntry.project = project;
            return this;
        }

        public Builder havingTask(TaskModel task) {
            timesheetEntry.task = task;
            return this;
        }

        public Builder withId(BigInteger id) {
            timesheetEntry.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            timesheetEntry.title = title;
            return this;
        }

        public Builder withState(State state) {
            timesheetEntry.state = state;
            return this;
        }

        public TimeSheetEntryModel build() {
            return timesheetEntry;
        }
    }

}
