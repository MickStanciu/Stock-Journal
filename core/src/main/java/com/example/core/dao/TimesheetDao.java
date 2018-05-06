package com.example.core.dao;

import com.example.core.model.ProjectModel;
import com.example.core.model.TaskModel;
import com.example.core.model.TimesheetEntryModel;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Stateless
public class TimesheetDao {

    private static final Logger log = Logger.getLogger(TimesheetDao.class);

    //todo: join on tenant fk as well
    private static final String TIMESHEET_READ_BY_ACCOUNT = "SELECT t.account_fk, t.from_time, t.to_time, " +
            "CAST(t.tenant_fk AS VARCHAR(36)) AS tenant_id, " +
            "p.id as project_id, p.title as project_title, p.active as project_active, p.description as project_description, " +
            "t2.id as task_id, t2.project_fk as task_project_fk, t2.active as task_active, t2.title as task_title, t2.description as task_description " +
            "FROM timesheet t " +
            "LEFT JOIN projects p on t.project_fk = p.id " +
            "LEFT JOIN tasks t2 on t.task_fk = t2.id " +
            "WHERE t.tenant_fk = CAST(:tenant_fk AS uuid) " +
            "and t.account_fk = :account_fk " +
            "and t.from_time = :from_time " +
            "and t.to_time = :to_time";

    @PersistenceContext
    private EntityManager em;

    public List<TimesheetEntryModel> getEntriesByIdAndTime(String tenantId, BigInteger accountId, Instant from, Instant to) {
        Query q = em.createNativeQuery(TIMESHEET_READ_BY_ACCOUNT);
        q.setParameter("tenant_fk", tenantId);
        q.setParameter("account_fk", accountId);
        q.setParameter("from_time", from);
        q.setParameter("to_time", to);

        List<Object[]> results = q.getResultList();
        if (results.size() == 0) {
            return Collections.emptyList();
        }

        List<TimesheetEntryModel> entryList = new ArrayList<>();
        for(Object[] result : results) {
            entryList.add(mapFromObject(result));
        }
        return entryList;
    }


    private TimesheetEntryModel mapFromObject(Object[] result) {
        ProjectModel project = ProjectModel.builder()
                .withTenantId((String) result[3])
                .withId((BigInteger) result[4])
                .withTitle((String) result[5])
                .active((boolean) result[6])
                .withDescription((String) result[7])
                .build();

        TaskModel task = TaskModel.builder()
                .withTenantId((String) result[3])
                .withId((BigInteger) result[8])
                .withProjectId((BigInteger) result[9])
                .active((boolean) result[10])
                .withTitle((String) result[11])
                .withDescription((String) result[12])
                .build();

        return TimesheetEntryModel.builder()
                .withAccountId((BigInteger) result[0])
                .withTenantId((String) result[3])
                .havingProject(project)
                .havingTask(task)
                .fromTime(((Timestamp) result[1]).toInstant())
                .toTime(((Timestamp) result[2]).toInstant())
                .build();
    }
}
