package org.jsuarez.springcloud.msvc.courses.services;

import org.jsuarez.springcloud.msvc.courses.clients.UserClientRest;
import org.jsuarez.springcloud.msvc.courses.models.UserDto;
import org.jsuarez.springcloud.msvc.courses.models.entity.Course;
import org.jsuarez.springcloud.msvc.courses.models.entity.CourseUser;
import org.jsuarez.springcloud.msvc.courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImp implements CourseService {

    @Autowired
    CourseRepository repository;

    @Autowired
    UserClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAll() {
        return (List<Course>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional()
    public Course save(Course course) {
        return repository.save(course);
    }

    @Override
    @Transactional()
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional()
    public Optional<UserDto> assignUser(UserDto user, Long courseId) {
        Optional<Course> o = repository.findById(courseId);
        if (o.isPresent()) {
            UserDto userMsvc = client.listById(user.getId());
            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());
            course.addCourseUser(courseUser);
            repository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional()
    public Optional<UserDto> createUser(UserDto user, Long courseId) {
        Optional<Course> o = repository.findById(courseId);
        if (o.isPresent()) {
            UserDto newUserMsvc = client.create(user);
            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(newUserMsvc.getId());
            course.addCourseUser(courseUser);
            repository.save(course);
            return Optional.of(newUserMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional()
    public Optional<UserDto> unassignUser(UserDto user, Long courseId) {
        Optional<Course> o = repository.findById(courseId);
        if (o.isPresent()) {
            UserDto userMsvc = client.listById(user.getId());
            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());
            course.removeCourseUser(courseUser);
            repository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }


}
