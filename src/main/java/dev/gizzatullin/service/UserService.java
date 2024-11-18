package dev.gizzatullin.service;

import dev.gizzatullin.model.user.User;

import java.util.Collection;

public interface UserService {

    User create(User user);

    User get(Long id);

    Collection<User> getAll();

    User update(long id, User user);

    void delete(Long id);
}
