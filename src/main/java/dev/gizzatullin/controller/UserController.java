package dev.gizzatullin.controller;

import dev.gizzatullin.model.user.User;
import dev.gizzatullin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public User addUser(@RequestBody User user) {
        log.info("Add user: {}", user);
        User userSaved = userService.create(user);
        log.info("Added user: {}", user);
        return userSaved;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        log.info("Get user: {}", id);
        User user = userService.get(id);
        log.info("Finish getting user: {}", user);
        return user;
    }

    @GetMapping
    public Collection<User> getAll() {
        log.info("Get all users");
        Collection<User> users = userService.getAll();
        log.info("Finish getting all users");
        return users;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User user) {
        log.info("Update user: {}", user);
        User userUpdated = userService.update(id, user);
        log.info("Finish updating user: {}", userUpdated);
        return userUpdated;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        log.info("Delete user: {}", id);
        userService.delete(id);
        log.info("Finish deleting user: {}", id);
    }
}
