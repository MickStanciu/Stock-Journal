package com.example.core.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;

public class TimesheetEntryModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //todo: might need an id
    private Instant fromTime;
    private Instant toTime;

    private String tenantId;
    private BigInteger accountId;

    private ProjectModel project;
    private TaskModel task;

    public TimesheetEntryModel() {
        //required by Jackson
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
        protected TimesheetEntryModel timesheetEntry;

        public Builder() {
            timesheetEntry = new TimesheetEntryModel();
        }

        public Builder(TimesheetEntryModel timesheetEntry) {
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

        public TimesheetEntryModel build() {
            return timesheetEntry;
        }
    }

}
