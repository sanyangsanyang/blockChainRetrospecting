package com.edu.nju.fabricservice.controller;

import com.edu.nju.fabricservice.entity.response.Response;
import com.edu.nju.fabricservice.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:yangsanyang
 * Time:2018/5/12 9:07 AM.
 * Illustration:
 */
@RestController
@RequestMapping("/api/server")
public class ServerController {
    
    private final ServerService serverService;
    
    @Autowired
    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }
    
    @RequestMapping("/start")
    Response startServer(){
        return serverService.startServer();
    }
    
    @RequestMapping("/stop")
    Response stopServer(){
        return serverService.stopServer();
    }
    
    @RequestMapping("/restart")
    Response restartServer(){
        return serverService.restartServer();
    }
}
