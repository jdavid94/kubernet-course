package org.jsuarez.springcloud.msvc.users.repositories;

import org.jsuarez.springcloud.msvc.users.models.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
