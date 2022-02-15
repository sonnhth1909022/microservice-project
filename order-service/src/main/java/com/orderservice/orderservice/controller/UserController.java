package com.orderservice.orderservice.controller;

import com.orderservice.orderservice.dto.user.UserDto;
import com.orderservice.orderservice.dto.user.UserMapper;
import com.orderservice.orderservice.entity.User;
import com.orderservice.orderservice.service.UserService;
import com.orderservice.orderservice.ulti.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("list")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(RESTResponse.success(userService.getAllUsers()
                ,"get all users successful!"), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        User user = userMapper.INSTANCE.userDtoToUser(userDto);
        userService.saveUser(user);
        return new ResponseEntity<>(RESTResponse.success(userDto
                ,"create user successful!"), HttpStatus.OK);
    }
}
