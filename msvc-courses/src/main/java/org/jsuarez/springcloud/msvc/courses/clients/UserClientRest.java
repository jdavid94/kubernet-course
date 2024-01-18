package org.jsuarez.springcloud.msvc.courses.clients;


import org.jsuarez.springcloud.msvc.courses.models.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-users", url="localhost:8001")
public interface UserClientRest {

    @GetMapping("/{id}")
    UserDto listById(@PathVariable Long id);

    @PostMapping("/")
    UserDto create(@RequestBody UserDto user);
}
