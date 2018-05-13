package com.edu.nju.businessclient.controller;

import com.edu.nju.businessclient.FeignClient.TestFeignClient;
import com.edu.nju.businessclient.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:yangsanyang
 * Time:2018/5/10 8:29 AM.
 * Illustration:
 */
@RequestMapping("/test")
@RestController
public class TestController {
    
    @Autowired
    private TestFeignClient testFeignClient;
    
    @GetMapping("/getStudent")
    public Student getStudent(int id){
        return testFeignClient.getStudent(id);
    }
}
