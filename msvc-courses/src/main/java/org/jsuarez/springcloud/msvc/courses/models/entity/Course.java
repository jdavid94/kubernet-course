package org.jsuarez.springcloud.msvc.courses.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.jsuarez.springcloud.msvc.courses.models.UserDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<CourseUser> courseUsers;

    @Transient
    private List<UserDto> users;

    public Course() {
    }

    public Course(Long id) {
        courseUsers = new ArrayList<>();
        users = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCourseUser(CourseUser courseUser) {
        courseUsers.add(courseUser);
    }

    public void removeCourseUser(CourseUser courseUser) {
        courseUsers.remove(courseUser);
    }

    public List<CourseUser> getCourseUsers() {
        return courseUsers;
    }

    public void setCourseUsers(List<CourseUser> courseUsers) {
        this.courseUsers = courseUsers;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
