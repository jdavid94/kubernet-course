package org.jsuarez.springcloud.msvc.courses.services;

import org.jsuarez.springcloud.msvc.courses.models.UserDto;
import org.jsuarez.springcloud.msvc.courses.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> getAll();
    Optional<Course> getById(Long id);
    Optional<Course> getByIdWithUsers(Long id);
    Course save(Course course);
    void delete(Long id);
    void deleteCourseUserById(Long id);

    Optional<UserDto> assignUser(UserDto user, Long courseId);
    Optional<UserDto> createUser(UserDto user, Long courseId);
    Optional<UserDto> unassignUser(UserDto user, Long courseId);
}
