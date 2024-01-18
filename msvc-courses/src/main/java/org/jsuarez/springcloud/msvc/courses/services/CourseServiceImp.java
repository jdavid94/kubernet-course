package org.jsuarez.springcloud.msvc.courses.services;

import org.jsuarez.springcloud.msvc.courses.entity.Course;
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
}
