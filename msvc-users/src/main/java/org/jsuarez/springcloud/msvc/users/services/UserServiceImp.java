package org.jsuarez.springcloud.msvc.users.services;

import org.jsuarez.springcloud.msvc.users.clients.CourseClientRest;
import org.jsuarez.springcloud.msvc.users.models.entity.User;
import org.jsuarez.springcloud.msvc.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private CourseClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return (List<User>) repository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<User> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
        client.deleteCourseUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllByIds(Iterable<Long> ids) {
        return (List<User>) repository.findAllById(ids);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
