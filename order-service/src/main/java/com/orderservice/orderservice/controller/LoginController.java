package com.orderservice.orderservice.controller;

import com.orderservice.orderservice.dto.user.LoginDto;
import com.orderservice.orderservice.entity.User;
import com.orderservice.orderservice.service.UserService;
import com.orderservice.orderservice.ulti.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.orderservice.orderservice.service.UserServiceImpl.userToken;

@RestController
@RequestMapping("api/v1/authentication/")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto){
        Optional<User> user = userService.findUserByUserNameAndPassword(loginDto.getUserName(), loginDto.getPassword());
        if(user.isPresent()){
            userToken = user.get().getUserId();
            return new ResponseEntity<>(RESTResponse.success(userToken
                    ,"Login successful!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                    .checkErrorWithMessage("Wrong username or password!")
                    .build(), HttpStatus.NOT_FOUND);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logoutUser(){
        if(userToken == ""){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .checkErrorWithMessage("You're already logged out!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        else {
            userToken = "";
            return new ResponseEntity<>(new RESTResponse.SuccessNoData()
                    .setMessage("Logout successful !")
                    .build(), HttpStatus.OK);
        }
    }
}
