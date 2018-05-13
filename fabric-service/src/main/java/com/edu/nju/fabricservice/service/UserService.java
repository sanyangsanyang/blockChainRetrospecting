package com.edu.nju.fabricservice.service;

import com.edu.nju.fabricservice.entity.response.Response;

/**
 * Author:yangsanyang
 * Time:2018/5/11 8:58 PM.
 * Illustration:
 */
public interface UserService {
    
    Response getAllUsers();
    
    Response registerUser(String userName);
}
