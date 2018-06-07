package com.example.timesheet.dao;

import com.example.timesheet.model.ProjectModel;
import com.example.timesheet.model.TaskModel;
import com.example.timesheet.model.TimeSheetEntryModel;
import com.example.timesheet.statemachine.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository("TimeSheetDaoReal")
public class TimeSheetDaoImpl implements TimeSheetDao {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

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

    @Autowired
    public TimeSheetDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, BigInteger accountId, LocalDateTime from, LocalDateTime to) {
        Object [] map = new Object[]{tenantId, accountId, Timestamp.valueOf(from), Timestamp.valueOf(to)};
        return jdbcTemplate.query(TIMESHEET_READ_BY_ACCOUNT, map, new TimeSheetEntryRowMapper());
    }
}

class TimeSheetEntryRowMapper implements RowMapper<TimeSheetEntryModel> {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetEntryRowMapper.class);

    @Override
    public TimeSheetEntryModel mapRow(ResultSet resultSet, int i) throws SQLException {
        String tenantId = resultSet.getString("tenant_id");
        BigInteger projectId = BigInteger.valueOf(resultSet.getLong("project_id"));

        ProjectModel project = ProjectModel.builder()
                .withTenantId(tenantId)
                .withId(projectId)
                .withTitle(resultSet.getString("project_title"))
                .active(resultSet.getBoolean("project_active"))
                .withDescription(resultSet.getString("project_description"))
                .build();

        TaskModel task = TaskModel.builder()
                .withTenantId(tenantId)
                .withId(BigInteger.valueOf(resultSet.getLong("task_id")))
                .withProjectId(projectId)
                .active(resultSet.getBoolean("task_active"))
                .withTitle(resultSet.getString("task_title"))
                .withDescription(resultSet.getString("task_description"))
                .build();

        State ts;
        try {
            ts = State.valueOf(resultSet.getString("status"));
        } catch (IllegalArgumentException ex) {
            ts = State.NOT_FILLED;
            log.error("Illegal state found", ex);
        }

        return TimeSheetEntryModel.builder()
                .withId(BigInteger.valueOf(resultSet.getLong("id")))
                .withAccountId(BigInteger.valueOf(resultSet.getLong("account_fk")))
                .withTenantId(tenantId)
                .havingProject(project)
                .havingTask(task)
                .fromTime(resultSet.getTimestamp("from_time").toInstant())
                .toTime(resultSet.getTimestamp("to_time").toInstant())
                .withTitle(resultSet.getString("title"))
                .withState(ts)
                .build();
    }
}