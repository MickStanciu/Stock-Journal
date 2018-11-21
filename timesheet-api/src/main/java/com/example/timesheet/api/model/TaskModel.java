package com.example.timesheet.api.model;

import java.io.Serializable;

public class TaskModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String tenantId;
    private long projectId;
    private boolean active;
    private String title;
    private String description;

    private TaskModel() {
        //required by Jackson
    }

    public long getId() {
        return id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public long getProjectId() {
        return projectId;
    }

    public boolean isActive() {
        return active;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        final TaskModel task;

        Builder() {
            task = new TaskModel();
        }

        public Builder(TaskModel task) {
            this.task = task;
        }

        public Builder withTenantId(String tenantId) {
            task.tenantId = tenantId;
            return this;
        }

        public Builder withId(long id) {
            task.id = id;
            return this;
        }

        public Builder active(boolean active) {
            task.active = active;
            return this;
        }

        public Builder withTitle(String title) {
            task.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            task.description = description;
            return this;
        }

        public Builder withProjectId(long projectId) {
            task.projectId = projectId;
            return this;
        }

        public TaskModel build() {
            return task;
        }
    }
}
