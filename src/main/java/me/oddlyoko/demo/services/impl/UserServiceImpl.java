package me.oddlyoko.demo.services.impl;

import me.oddlyoko.demo.models.User;
import me.oddlyoko.demo.repositories.UserRepository;
import me.oddlyoko.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> getUser(long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        return getUser(userId).map(this::deleteUser).orElse(false);
    }

    @Override
    public boolean deleteUser(User user) {
        try {
            userRepository.delete(user);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            // Try to delete a resource but the database returned an empty row
            return false;
        }
    }
}
