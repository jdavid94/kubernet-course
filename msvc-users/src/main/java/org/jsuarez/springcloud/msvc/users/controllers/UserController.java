package org.jsuarez.springcloud.msvc.users.controllers;

import jakarta.validation.Valid;
import org.jsuarez.springcloud.msvc.users.models.entity.User;
import org.jsuarez.springcloud.msvc.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public List<User> list() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listById(@PathVariable Long id) {
        Optional<User> optionalUser = service.getById(id);
        if (optionalUser.isPresent()) {
          return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (!user.getEmail().isBlank() && service.getByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("Error", "User already exist with that email"));
        }
        if (result.hasErrors()) {
            return validated(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validated(result);
        }
        Optional<User> optionalUser = service.getById(id);
        if (optionalUser.isPresent()) {
            User userDb = optionalUser.get();
            if (!user.getEmail().isBlank() && !user.getEmail().equalsIgnoreCase(userDb.getEmail()) &&
                    service.getByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("Error", "Email already exist"));
            }
            userDb.setName(user.getName());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    service.save(userDb)
            );

        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<User> optionalUser = service.getById(id);
        if (optionalUser.isPresent()) {
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
