package dev.gizzatullin.service;

import dev.gizzatullin.exception.EntityNotFoundException;
import dev.gizzatullin.model.user.User;
import dev.gizzatullin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        log.info("Creating user {}", user);
        User savedUser = userRepository.save(user);
        log.info("Created user {}", savedUser);
        return savedUser;
    }

    @Override
    public User get(Long id) {
        log.info("Retrieving user {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        log.info("Retrieved user {}", user);
        return user;
    }

    @Override
    public Collection<User> getAll() {
        log.info("Retrieving all users");
        Collection<User> users = userRepository.findAll();
        log.info("Retrieved all users");
        return users;
    }

    @Override
    public User update(long id, User user) {
        log.info("Updating user {}", user);
        User savedUser = userRepository.update(user);
        log.info("Updated user {}", savedUser);
        return savedUser;
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user with id {}", id);
        userRepository.deleteById(id);
        log.info("Deleted user with id {}", id);
    }
}
