package com.example.timesheet.model;

import java.io.Serializable;

public class ProjectModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String tenantId;
    private boolean active;
    private String title;
    private String description;

    private ProjectModel() {
        //required by Jackson
    }

    public long getId() {
        return id;
    }

    public String getTenantId() {
        return tenantId;
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
        return "ProjectModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        final ProjectModel project;

        Builder() {
            project = new ProjectModel();
        }

        public Builder(ProjectModel project) {
            this.project = project;
        }

        public Builder withTenantId(String tenantId) {
            project.tenantId = tenantId;
            return this;
        }

        public Builder withId(long id) {
            project.id = id;
            return this;
        }

        public Builder active(boolean active) {
            project.active = active;
            return this;
        }

        public Builder withTitle(String title) {
            project.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            project.description = description;
            return this;
        }

        public ProjectModel build() {
            return project;
        }
    }
}
