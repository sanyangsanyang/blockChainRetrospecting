package com.edu.nju.fabricservice.controller;

import com.edu.nju.fabricservice.entity.response.Response;
import com.edu.nju.fabricservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:yangsanyang
 * Time:2018/5/12 9:06 AM.
 * Illustration:
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @RequestMapping("/allUser")
    Response getAllUsers(){
        return userService.getAllUsers();
    }
    
    @RequestMapping("/registerUser")
    Response registerUser(String userName){
        return userService.registerUser(userName);
    }
}
