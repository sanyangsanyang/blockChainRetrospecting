package com.edu.nju.fabricservice.service;

import com.edu.nju.fabricservice.entity.response.Response;

/**
 * Author:yangsanyang
 * Time:2018/5/11 8:57 PM.
 * Illustration:
 */
public interface QueryService {
    
    Response getItem(String itemId);
    
    Response getAllItems();
    
    Response getItemHistory(String itemId);
}
