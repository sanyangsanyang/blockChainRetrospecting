package com.edu.nju.businessclient.controller;

import com.edu.nju.businessclient.FeignClient.BusinessFeignClient;
import com.edu.nju.businessclient.entity.ChangeItemInfo;
import com.edu.nju.businessclient.entity.DeleteItemInfo;
import com.edu.nju.businessclient.entity.ItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:yangsanyang
 * Time:2018/5/12 4:04 PM.
 * Illustration:
 */
@RestController
@RequestMapping("/business")
public class BusinessController {
    
    
    private final BusinessFeignClient businessFeignClient;
    
    @Autowired
    public BusinessController(BusinessFeignClient businessFeignClient) {
        this.businessFeignClient = businessFeignClient;
    }
    
    
    @RequestMapping("/addItem")
    String addItem(@RequestBody ItemInfo itemInfo){
        return businessFeignClient.addItem(itemInfo);
    }
    
    @RequestMapping("/changeItem")
    String changeItem(@RequestBody ChangeItemInfo changeItemInfo){
        return businessFeignClient.changeItem(changeItemInfo);
    }
    
    @RequestMapping("/expireItem")
    String expireItem(@RequestBody DeleteItemInfo deleteItemInfo){
        return businessFeignClient.expireItem(deleteItemInfo);
    }
    
    @RequestMapping("/getItem")
    String getItem(@RequestParam("itemId") String itemId){
        return businessFeignClient.getItem(itemId);
    }
    
    @RequestMapping("/getAllItems")
    String getAllItems(){
        return businessFeignClient.getAllItems();
    }
    
    @RequestMapping("/getItemHistory")
    String getItemHistory(@RequestParam("itemId") String itemId){
        return businessFeignClient.getItemHistory(itemId);
    }
}
