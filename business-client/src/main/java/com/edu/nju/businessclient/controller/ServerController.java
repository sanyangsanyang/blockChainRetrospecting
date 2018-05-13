package com.edu.nju.businessclient.controller;

import com.edu.nju.businessclient.FeignClient.ServerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:yangsanyang
 * Time:2018/5/12 4:05 PM.
 * Illustration:
 */
@RequestMapping("/server")
@RestController
public class ServerController {
    
    private final ServerFeignClient serverFeignClient;
    
    @Autowired
    public ServerController(ServerFeignClient serverFeignClient) {
        this.serverFeignClient = serverFeignClient;
    }
    
    @RequestMapping("/start")
    String startServer(){
        return serverFeignClient.startServer();
    }
    
    @RequestMapping("/stop")
    String stopServer(){
        return serverFeignClient.stopServer();
    }
    
    @RequestMapping("/restart")
    String restartServer(){
        return serverFeignClient.restartServer();
    }
}
