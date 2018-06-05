package com.example.timesheet.repository;

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
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TimeSheetRepository {

    private static final Logger log = LoggerFactory.getLogger(TimeSheetRepository.class);


    private DataSource dataSource;
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
    public TimeSheetRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly=true)
    public List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, BigInteger accountId, LocalDateTime from, LocalDateTime to) {
        Object [] map = new Object[]{tenantId, accountId, Timestamp.valueOf(from), Timestamp.valueOf(to)};
        return jdbcTemplate.query(TIMESHEET_READ_BY_ACCOUNT, map, new TimeSheetEntryRowMapper());
    }
}

class TimeSheetEntryRowMapper implements RowMapper<TimeSheetEntryModel> {

    @Override
    public TimeSheetEntryModel mapRow(ResultSet resultSet, int i) throws SQLException {
        return TimeSheetEntryModel.builder()
                .withId(BigInteger.ONE)
                .withAccountId(BigInteger.ONE)
                .withTenantId("!23")
                .havingProject(null)
                .havingTask(null)
                .fromTime(null)
                .toTime(null)
                .withTitle("demo")
                .withState(null)
                .build();
    }

    private TimeSheetEntryModel mapFromObject(Object[] result) {
        String tenantId = (String) result[6];

        ProjectModel project = ProjectModel.builder()
                .withTenantId(tenantId)
                .withId((BigInteger) result[7])
                .withTitle((String) result[8])
                .active((boolean) result[9])
                .withDescription((String) result[10])
                .build();

        TaskModel task = TaskModel.builder()
                .withTenantId(tenantId)
                .withId((BigInteger) result[11])
                .withProjectId((BigInteger) result[12])
                .active((boolean) result[13])
                .withTitle((String) result[14])
                .withDescription((String) result[15])
                .build();

        State ts;
        try {
            ts = State.valueOf((String) result[5]);
        } catch (IllegalArgumentException ex) {
            ts = State.NOT_FILLED;
//            log.error("Illegal state found: " + result[5], ex);
        }

        return TimeSheetEntryModel.builder()
                .withId((BigInteger) result[0])
                .withAccountId((BigInteger) result[1])
                .withTenantId(tenantId)
                .havingProject(project)
                .havingTask(task)
                .fromTime(((Timestamp) result[2]).toInstant())
                .toTime(((Timestamp) result[3]).toInstant())
                .withTitle((String) result[4])
                .withState(ts)
                .build();
    }
}