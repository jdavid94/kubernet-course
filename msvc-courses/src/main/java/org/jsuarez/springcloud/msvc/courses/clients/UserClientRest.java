package org.jsuarez.springcloud.msvc.courses.clients;


import org.jsuarez.springcloud.msvc.courses.models.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-users", url="${msvc.users.url}")
public interface UserClientRest {

    @GetMapping("/{id}")
    UserDto listById(@PathVariable Long id);

    @PostMapping("/")
    UserDto create(@RequestBody UserDto user);

    @GetMapping("/users-by-courses")
    List<UserDto> findAllByIds(@RequestParam Iterable<Long> ids);
}
