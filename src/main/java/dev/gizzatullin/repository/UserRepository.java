package dev.gizzatullin.repository;

import dev.gizzatullin.model.user.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Collection<User> findAll();

    User update(User user);

    void deleteById(Long id);
}
