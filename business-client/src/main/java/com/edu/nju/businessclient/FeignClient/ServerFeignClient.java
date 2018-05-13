package com.edu.nju.businessclient.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author:yangsanyang
 * Time:2018/5/12 3:57 PM.
 * Illustration:
 */
@Component
@FeignClient(value = "fabric-service")
@RequestMapping("/api/server")
public interface ServerFeignClient {
    
    @RequestMapping("/start")
    String startServer();
    
    @RequestMapping("/stop")
    String stopServer();
    
    @RequestMapping("/restart")
    String restartServer();
}
