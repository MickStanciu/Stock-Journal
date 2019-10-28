package com.example.tradelog.api.repository;

import org.springframework.stereotype.Repository;
import com.example.tradelog.api.gen.Course;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CourseRepository {

    Map<Integer, Course> courses;

    public CourseRepository() {
        courses = new HashMap<>(4);
        courses.put(1, Course.newBuilder().setId(1).setName("one").build());
        courses.put(2, Course.newBuilder().setId(2).setName("two").build());
        courses.put(3, Course.newBuilder().setId(3).setName("three").build());
        courses.put(4, Course.newBuilder().setId(4).setName("four").build());
    }

    public Course getCourse(int id) {
        return courses.get(id);
    }
}
