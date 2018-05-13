package com.edu.nju.fabricservice.service;

import com.edu.nju.fabricservice.entity.po.*;
import com.edu.nju.fabricservice.service.impl.InvokeServiceImpl;
import com.edu.nju.fabricservice.service.impl.QueryServiceImpl;
import com.google.gson.Gson;

/**
 * Author:yangsanyang
 * Time:2018/5/11 10:13 PM.
 * Illustration:
 */
public class InvokerAndQueryTest {
    
    public static void main(String[] args) {
        
//        InvokeService invokeService = new InvokeServiceImpl();
//        QueryService queryService = new QueryServiceImpl();
//
        ItemInfo itemInfo1 = new ItemInfo("asset1" , new EnvironmentStatus(100,200,"南京") , new FixedInfo("器材" , "12345" , "2018-01-01-01:01:01") , false);
        ChangeItemInfo changeItemInfo = new ChangeItemInfo("asset1" , new EnvironmentStatus(50,150,"北京"));
        DeleteItemInfo deleteItemInfo = new DeleteItemInfo("asset1" , true);
        ItemInfo itemInfo2 = new ItemInfo("asset2" , new EnvironmentStatus(194,255,"美国") , new FixedInfo("哑铃" , "66666" , "2016-01-01-01:01:01") , false);
         Gson gson = new Gson();
        System.out.println(gson.toJson(itemInfo1));
        System.out.println(gson.toJson(changeItemInfo));
        System.out.println(gson.toJson(deleteItemInfo));
        System.out.println(gson.toJson(itemInfo2));
//        invokeService.addItem(itemInfo1);
//        queryService.getItem("asset1");
//        invokeService.changeItem(changeItemInfo);
//        queryService.getItem("asset1");
//        invokeService.expireItem(deleteItemInfo);
//        System.out.println(gson.toJson(queryService.getItem("asset1")));
//        invokeService.addItem(itemInfo2);
//        System.out.println(gson.toJson(queryService.getAllItems()));
//        System.out.println(gson.toJson(queryService.getItemHistory("asset1")));
    }
}
