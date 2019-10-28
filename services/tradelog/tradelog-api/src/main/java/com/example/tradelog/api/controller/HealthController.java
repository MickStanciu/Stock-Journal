package com.example.tradelog.api.controller;

import com.example.tradelog.api.exception.ResourceErrorException;
import com.example.tradelog.api.repository.CourseRepository;
import com.example.tradelog.api.service.HealthService;
import com.example.tradelog.api.spec.exception.ExceptionCode;
import com.example.tradelog.api.spec.model.HealthModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/health", produces = "application/json")
public class HealthController {

    private HealthService healthService;
    private CourseRepository courseRepository;

    public HealthController(HealthService healthService, CourseRepository courseRepository) {
        this.healthService = healthService;
        this.courseRepository = courseRepository;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public HealthModel check() {
        return healthService.getHealthModel();
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public void pong() {

    }

    @RequestMapping(value = "/test500", method = RequestMethod.GET)
    public void test500() {
        throw new ResourceErrorException(ExceptionCode.UNKNOWN);
    }

    @RequestMapping(value = "/courses/{id}", method = RequestMethod.GET)
    public com.example.tradelog.api.gen.Course course(@PathVariable Integer id) {
        return courseRepository.getCourse(id);
    }
}
