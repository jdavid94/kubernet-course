package org.jsuarez.springcloud.msvc.courses.repositories;

import org.jsuarez.springcloud.msvc.courses.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
