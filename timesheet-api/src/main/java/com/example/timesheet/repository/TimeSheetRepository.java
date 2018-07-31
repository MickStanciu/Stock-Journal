package com.example.timesheet.repository;

import com.example.timesheet.model.ProjectModel;
import com.example.timesheet.model.TaskModel;
import com.example.timesheet.model.TimeSheetEntryModel;
import com.example.timesheet.statemachine.State;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class TimeSheetRepository implements TimeSheetDao {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetRepository.class);

    //todo: join on tenant fk as well
    private static final String TIMESHEET_READ_BY_ACCOUNT = "SELECT t.id, t.account_fk, t.from_time, t.to_time, t.title, t.status, " +
            "CAST(t.tenant_fk AS VARCHAR(36)) AS tenant_id, " +
            "p.id as project_id, p.title as project_title, p.active as project_active, p.description as project_description, " +
            "t2.id as task_id, t2.project_fk as task_project_fk, t2.active as task_active, t2.title as task_title, t2.description as task_description " +
            "FROM timesheet t " +
            "LEFT JOIN projects p on t.project_fk = p.id " +
            "LEFT JOIN tasks t2 on t.task_fk = t2.id " +
            "WHERE t.tenant_fk = CAST(? AS uuid) " +
            "and t.account_fk = ? " +
            "and t.from_time >= ? " +
            "and t.to_time <= ?";

    private DatabaseConnection conn;

    @Inject
    public TimeSheetRepository(DatabaseConnection conn) {
        this.conn = conn;
    }

    @Override
    public List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, long accountId, LocalDateTime from, LocalDateTime to) {
        return conn.getJdbi().withHandle(handle ->
                handle
                    .createQuery(TIMESHEET_READ_BY_ACCOUNT)
                    .bind(0, tenantId)
                    .bind(1, accountId)
                    .bind(2, Timestamp.valueOf(from))
                    .bind(3, Timestamp.valueOf(to))
                    .map(new TimeSheetEntryRowMapper())
                    .list()
        );
    }
}

class TimeSheetEntryRowMapper implements RowMapper<TimeSheetEntryModel> {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetEntryRowMapper.class);

    @Override
    public TimeSheetEntryModel map(ResultSet rs, StatementContext ctx) throws SQLException {
        String tenantId = rs.getString("tenant_id");
        long projectId = rs.getLong("project_id");

        ProjectModel project = ProjectModel.builder()
            .withTenantId(tenantId)
            .withId(projectId)
            .withTitle(rs.getString("project_title"))
            .active(rs.getBoolean("project_active"))
            .withDescription(rs.getString("project_description"))
            .build();

            TaskModel task = TaskModel.builder()
                .withTenantId(tenantId)
                .withId(rs.getLong("task_id"))
                .withProjectId(projectId)
                .active(rs.getBoolean("task_active"))
                .withTitle(rs.getString("task_title"))
                .withDescription(rs.getString("task_description"))
                .build();

        State ts;
        try {
            ts = State.valueOf(rs.getString("status"));
        } catch (IllegalArgumentException ex) {
            ts = State.NOT_FILLED;
            log.error("Illegal state found", ex);
        }

        return TimeSheetEntryModel.builder()
                .withId(rs.getLong("id"))
                .withAccountId(rs.getLong("account_fk"))
                .withTenantId(tenantId)
                .havingProject(project)
                .havingTask(task)
                .fromTime(rs.getTimestamp("from_time").toInstant())
                .toTime(rs.getTimestamp("to_time").toInstant())
                .withTitle(rs.getString("title"))
                .withState(ts)
                .build();
    }
}