package com.edu.nju.fabricservice.service;

import com.edu.nju.fabricservice.entity.response.Response;

/**
 * Author:yangsanyang
 * Time:2018/5/12 7:21 AM.
 * Illustration:
 */
public interface ServerService {
    
    Response startServer();
    
    Response stopServer();
    
    Response restartServer();
}
