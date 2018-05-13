package com.edu.nju.businessclient.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author:yangsanyang
 * Time:2018/5/12 3:58 PM.
 * Illustration:
 */
@Component
@FeignClient(value = "fabric-service")
@RequestMapping("/api/user")
public interface UserFeignClient {
    
    @RequestMapping("/allUser")
    String getAllUsers();
    
    @RequestMapping("/registerUser")
    String registerUser(@RequestParam("userName") String userName);
}
