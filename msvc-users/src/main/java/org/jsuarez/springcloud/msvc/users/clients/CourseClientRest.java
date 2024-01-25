package org.jsuarez.springcloud.msvc.users.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-courses", url = "localhost:8090")
public interface CourseClientRest {

    @DeleteMapping("/delete-user/{id}")
    void deleteCourseUserById(@PathVariable Long id);

}