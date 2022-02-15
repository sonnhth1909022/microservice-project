package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    Optional<User> findUserById(String id);
}
