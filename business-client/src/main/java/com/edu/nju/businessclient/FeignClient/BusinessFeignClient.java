package com.edu.nju.businessclient.FeignClient;

import com.edu.nju.businessclient.entity.ChangeItemInfo;
import com.edu.nju.businessclient.entity.DeleteItemInfo;
import com.edu.nju.businessclient.entity.ItemInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author:yangsanyang
 * Time:2018/5/12 3:56 PM.
 * Illustration:
 */
@Component
@FeignClient(value = "fabric-service")
@RequestMapping("/api/business")
public interface BusinessFeignClient {
    
    @PostMapping("/addItem")
    String addItem(@RequestBody ItemInfo itemInfo);
    
    @PostMapping("/changeItem")
    String changeItem(@RequestBody ChangeItemInfo changeItemInfo);
    
    @PostMapping("/expireItem")
    String expireItem(@RequestBody DeleteItemInfo deleteItemInfo);
    
    @RequestMapping("/getItem")
    String getItem(@RequestParam("itemId") String itemId);
    
    @RequestMapping("/getAllItems")
    String getAllItems();
    
    @RequestMapping("/getItemHistory")
    String getItemHistory(@RequestParam("itemId") String itemId);
}
