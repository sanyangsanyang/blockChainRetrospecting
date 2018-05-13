package com.edu.nju.fabricservice.controller;

import com.edu.nju.fabricservice.entity.po.ItemInfo;
import com.edu.nju.fabricservice.entity.po.ChangeItemInfo;
import com.edu.nju.fabricservice.entity.po.DeleteItemInfo;
import com.edu.nju.fabricservice.entity.response.Response;
import com.edu.nju.fabricservice.service.InvokeService;
import com.edu.nju.fabricservice.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:yangsanyang
 * Time:2018/5/12 9:24 AM.
 * Illustration:
 */
@RestController
@RequestMapping("/api/business")
public class BusinessController {
    
    private final InvokeService invokeService;
    
    private final QueryService queryService;
    
    @Autowired
    public BusinessController(InvokeService invokeService, QueryService queryService) {
        this.invokeService = invokeService;
        this.queryService = queryService;
    }
    
    
    @RequestMapping("/addItem")
    Response addItem(@RequestBody ItemInfo itemInfo){
        return invokeService.addItem(itemInfo);
    }
    
    @RequestMapping("/changeItem")
    Response changeItem(@RequestBody ChangeItemInfo changeItemInfo){
        return invokeService.changeItem(changeItemInfo);
    }
    
    @RequestMapping("/expireItem")
    Response expireItem(@RequestBody DeleteItemInfo deleteItemInfo){
        return invokeService.expireItem(deleteItemInfo);
    }
    
    @RequestMapping("/getItem")
    Response getItem(String itemId){
        return queryService.getItem(itemId);
    }
    
    @RequestMapping("/getAllItems")
    Response getAllItems(){
        return queryService.getAllItems();
    }
    
    @RequestMapping("/getItemHistory")
    Response getItemHistory(String itemId){
        return queryService.getItemHistory(itemId);
    }
}
