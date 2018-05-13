package com.edu.nju.businessclient.controller;

import com.edu.nju.businessclient.FeignClient.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:yangsanyang
 * Time:2018/5/12 4:04 PM.
 * Illustration:
 */
@RequestMapping("/user")
@RestController
public class UserController {
    
    private final UserFeignClient userFeignClient;
    
    @Autowired
    public UserController(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }
    
    @RequestMapping("/allUser")
    String getAllUsers(){
        return userFeignClient.getAllUsers();
    }
    
    @RequestMapping("/registerUser")
    String registerUser(@RequestParam("userName") String userName){
        return userFeignClient.registerUser(userName);
    }
}
