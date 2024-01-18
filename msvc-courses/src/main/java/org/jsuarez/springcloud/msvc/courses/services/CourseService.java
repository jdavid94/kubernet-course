package org.jsuarez.springcloud.msvc.courses.services;

import org.jsuarez.springcloud.msvc.courses.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> getAll();
    Optional<Course> getById(Long id);
    Course save(Course course);
    void delete(Long id);
}
