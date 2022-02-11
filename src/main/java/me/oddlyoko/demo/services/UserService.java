package me.oddlyoko.demo.services;

import me.oddlyoko.demo.models.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {

    Page<User> getAllUsers(int page, int size);

    Optional<User> getUser(long userId);

    Optional<User> getUser(String email);

    User addUser(User user);

    User updateUser(User user);

    boolean deleteUser(long userId);

    boolean deleteUser(User user);
}
