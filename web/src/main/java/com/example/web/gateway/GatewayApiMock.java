package com.example.web.gateway;

import com.example.account.model.AccountModel;
import com.example.account.model.RoleInfoModel;
import com.example.account.model.RoleModel;
import com.example.common.converter.TimeConversion;
import com.example.core.model.ProjectModel;
import com.example.core.model.TaskModel;
import com.example.core.model.TimeSheetEntryModel;
import com.example.gatewayapi.model.AuthTokenModel;
import com.example.web.configuration.InjectionType;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Stateless
@InjectionType(isMock = true)
public class GatewayApiMock implements GatewayApi {

    private static final Logger log = Logger.getLogger(GatewayApiMock.class);
    private static final String DEFAULT_TENANT_ID = "d79ec11a-2011-4423-ba01-3af8de0a3e10";
    private static final BigInteger DEFAULT_ACCOUNT_ID = BigInteger.valueOf(6);
    private static final String DEFAULT_ACCOUNT_NAME = "Lola Demo";
    private static final String DEFAULT_ACCOUNT_PASSWORD = "secret";
    private static final String DEFAULT_ACCOUNT_EMAIL = "lola.demo@jadebaboon.com";
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJCZW5kaXMiLCJzdWIiOiJhdXRoIiwidGVuYW50SWQiOiJkNzllYzExYS0yMDExLTQ0MjMtYmEwMS0zYWY4ZGUwYTNlMTAiLCJhY2NvdW50SWQiOjYsInJvbGVJZCI6NywiaWF0IjoxNTI1ODYwMDc5LCJleHAiOjE1MjcwNjk2Nzl9.ZFiQJ2fsGWYTkBQOymSgT4M-mUWjG6SiExYWHVSXWA4";

    @PostConstruct
    public void init() {
        log.warn("WARNING: THIS IS GATEWAY API MOCK");
    }

    @Override
    public AuthTokenModel authenticate(String tenantId, String email, String password) {
        return new AuthTokenModel(TOKEN);
    }

    @Override
    public Optional<AccountModel> getAccount(String token, BigInteger accountId) {

        Set<RoleInfoModel> roles = new HashSet<>();
        roles.add(RoleInfoModel.LOG_IN);
        roles.add(RoleInfoModel.TIMESHEET_INSERT);

        return Optional.of(new AccountModel.Builder()
                .havingPersonalDetails()
                    .withTenantId(DEFAULT_TENANT_ID)
                    .withId(DEFAULT_ACCOUNT_ID)
                    .withEmail(DEFAULT_ACCOUNT_EMAIL)
                    .withName(DEFAULT_ACCOUNT_NAME)
                    .withPassword(DEFAULT_ACCOUNT_PASSWORD)
                    .withFlagActive(true)
                .havingRole()
                    .withRole(RoleModel.builder()
                            .withId(6)
                            .withName("role name")
                            .withPermissions(roles)
                            .build())
                .build());
    }

    @Override
    public List<TimeSheetEntryModel> getEntries(String tenantId, BigInteger accountId, LocalDateTime fromDate, LocalDateTime toDate) {
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
                            .fromTime(fromDate.plusHours(i).toInstant(ZoneOffset.UTC))
                            .toTime(fromDate.plusHours(i).plusMinutes(30 - 1).toInstant(ZoneOffset.UTC))
                            .havingProject(projectModel)
                            .havingTask(TaskModel.builder().withTitle("Task 1 - h" + i).build())
                            .withAccountId(BigInteger.ONE)
                            .withTenantId("123")
                            .build());

            timesheetEntries.add(
                    TimeSheetEntryModel.builder()
                            .fromTime(fromDate.plusHours(i).plusMinutes(30).toInstant(ZoneOffset.UTC))
                            .toTime(fromDate.plusHours(i+1).minusMinutes(1).toInstant(ZoneOffset.UTC))
                            .havingProject(projectModel)
                            .havingTask(TaskModel.builder().withTitle("Task 1 - h" + i).build())
                            .withAccountId(BigInteger.ONE)
                            .withTenantId("123")
                            .build());
        }

        timesheetEntries.add(
                TimeSheetEntryModel.builder()
                        .fromTime(fromDate.plusHours(17).plusMinutes(30).toInstant(ZoneOffset.UTC))
                        .toTime(fromDate.plusHours(17).plusMinutes(59).toInstant(ZoneOffset.UTC))
                        .havingProject(ProjectModel.builder().withTitle("Slot 35").build())
                        .havingTask(TaskModel.builder().withTitle("Task 1 - h17:30").build())
                        .withAccountId(BigInteger.ONE)
                        .withTenantId("123")
                        .build());

        return timesheetEntries;
    }
}
