//package com.example.timesheet.config;
//
//import com.example.timesheet.repository.TimeSheetDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AppConfiguration {
//
//    private ApplicationContext context;
//
//    @Autowired
//    public AppConfiguration(ApplicationContext context) {
//        this.context = context;
//    }
//
//    @Bean
//    public TimeSheetDao TimeSheet(@Value("${inject.timesheetdao.class}") String qualifier) {
//        return (TimeSheetDao) context.getBean(qualifier);
//    }
//
//}
