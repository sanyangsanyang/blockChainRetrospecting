package com.edu.nju.businessclient.FeignClient;

import com.edu.nju.businessclient.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author:yangsanyang
 * Time:2018/5/10 8:32 AM.
 * Illustration:
 */
@Component
@FeignClient(value = "fabric-service")
@RequestMapping("/test")
public interface TestFeignClient {
    
    @GetMapping("/student")
    Student getStudent(@RequestParam("id") int id);
    
}
