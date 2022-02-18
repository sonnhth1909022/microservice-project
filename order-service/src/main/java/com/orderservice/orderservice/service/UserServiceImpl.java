package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.User;
import com.orderservice.orderservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    public static String userToken = "";

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(String id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public Optional<User> findUserByUserNameAndPassword(String userName, String password) {
        return userRepository.findByUserNameAndAndPassword(userName, password);
    }


}
