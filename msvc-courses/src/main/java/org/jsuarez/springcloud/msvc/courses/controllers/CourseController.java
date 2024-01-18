package org.jsuarez.springcloud.msvc.courses.controllers;

import jakarta.validation.Valid;
import org.jsuarez.springcloud.msvc.courses.models.entity.Course;
import org.jsuarez.springcloud.msvc.courses.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CourseController {

    @Autowired
    private CourseService service;

    @GetMapping("/")
    public ResponseEntity<List<Course>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listById(@PathVariable Long id) {
        Optional<Course> optionalCourse = service.getById(id);
        if (optionalCourse.isPresent()) {
            return ResponseEntity.ok(optionalCourse.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Course Course, BindingResult result) {
        if (result.hasErrors()) {
            return validated(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(Course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody Course Course, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validated(result);
        }
        Optional<Course> optionalCourse = service.getById(id);
        if (optionalCourse.isPresent()) {
            Course CourseDb = optionalCourse.get();
            CourseDb.setName(Course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    service.save(CourseDb)
            );

        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Course> optionalCourse = service.getById(id);
        if (optionalCourse.isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    private ResponseEntity<Map<String, String>> validated(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "Error : " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
