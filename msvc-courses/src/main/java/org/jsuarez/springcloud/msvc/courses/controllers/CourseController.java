package org.jsuarez.springcloud.msvc.courses.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.jsuarez.springcloud.msvc.courses.models.UserDto;
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
        Optional<Course> optionalCourse = service.getByIdWithUsers(id); // service.getById(id);
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

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody UserDto user, @PathVariable Long courseId) {
        Optional<UserDto> o = null;
        try {
            o = service.assignUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("Error : ",
                    "User ID does not exits or Bad Communication " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody UserDto user, @PathVariable Long courseId) {
        Optional<UserDto> o = null;
        try {
            o = service.createUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("Error : ",
                    "User Cannot Be Created or Bad Communication " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/unassign-user/{courseId}")
    public ResponseEntity<?> unassignUser(@RequestBody UserDto user, @PathVariable Long courseId) {
        Optional<UserDto> o = null;
        try {
            o = service.unassignUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("Error : ",
                    "User ID does not exits or Bad Communication " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteCourseUserById(@PathVariable Long id) {
        service.deleteCourseUserById(id);
        return ResponseEntity.noContent().build();
    }


    private ResponseEntity<Map<String, String>> validated(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "Error : " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
