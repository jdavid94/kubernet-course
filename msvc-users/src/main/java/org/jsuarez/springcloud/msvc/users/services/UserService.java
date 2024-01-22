package org.jsuarez.springcloud.msvc.users.services;

import org.jsuarez.springcloud.msvc.users.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();
    Optional<User> getById (Long id);
    User save (User user);
    void delete(Long id);
    List<User> getAllByIds(Iterable<Long> ids);
    Optional<User> getByEmail(String email);
    boolean existsByEmail(String email);
}
