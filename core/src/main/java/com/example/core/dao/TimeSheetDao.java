package com.example.core.dao;

import com.example.core.model.ProjectModel;
import com.example.core.model.TaskModel;
import com.example.core.model.TimeSheetEntryModel;
import com.example.core.statemachine.State;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Stateless
public class TimeSheetDao {

    private static final Logger log = Logger.getLogger(TimeSheetDao.class);

    //todo: join on tenant fk as well
    private static final String TIMESHEET_READ_BY_ACCOUNT = "SELECT t.id, t.account_fk, t.from_time, t.to_time, t.title, t.status, " +
            "CAST(t.tenant_fk AS VARCHAR(36)) AS tenant_id, " +
            "p.id as project_id, p.title as project_title, p.active as project_active, p.description as project_description, " +
            "t2.id as task_id, t2.project_fk as task_project_fk, t2.active as task_active, t2.title as task_title, t2.description as task_description " +
            "FROM timesheet t " +
            "LEFT JOIN projects p on t.project_fk = p.id " +
            "LEFT JOIN tasks t2 on t.task_fk = t2.id " +
            "WHERE t.tenant_fk = CAST(:tenant_fk AS uuid) " +
            "and t.account_fk = :account_fk " +
            "and t.from_time >= :from_time " +
            "and t.to_time <= :to_time";

    @PersistenceContext
    private EntityManager em;

    public List<TimeSheetEntryModel> getEntriesByIdAndTime(String tenantId, BigInteger accountId, LocalDateTime from, LocalDateTime to) {
        Query q = em.createNativeQuery(TIMESHEET_READ_BY_ACCOUNT);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("account_fk", accountId);
        q.setParameter("from_time", Timestamp.valueOf(from));
        q.setParameter("to_time", Timestamp.valueOf(to));

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return Collections.emptyList();
        }

        List<TimeSheetEntryModel> entryList = new ArrayList<>();
        for(Object[] result : results) {
            entryList.add(mapFromObject(result));
        }
        return entryList;
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
            log.error("Illegal state found: " + result[5], ex);
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
