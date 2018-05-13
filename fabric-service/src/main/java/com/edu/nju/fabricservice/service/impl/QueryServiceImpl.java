package com.edu.nju.fabricservice.service.impl;

import com.edu.nju.fabricservice.entity.po.ItemInfo;
import com.edu.nju.fabricservice.entity.response.AllItemInfo;
import com.edu.nju.fabricservice.entity.response.GenericResponse;
import com.edu.nju.fabricservice.entity.response.ItemHistoryInfo;
import com.edu.nju.fabricservice.entity.response.Response;
import com.edu.nju.fabricservice.service.QueryService;
import com.edu.nju.fabricservice.utility.commmandlinetool.CommandHelper;
import com.edu.nju.fabricservice.utility.config.Config;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:yangsanyang
 * Time:2018/5/11 9:21 PM.
 * Illustration:
 */
@Service
public class QueryServiceImpl implements QueryService {
    
    
    private Config config = Config.getInstance();
    
    @Override
    public Response getItem(String itemId) {
        String result = run(new String []{"method" , "getItem" , "itemId" , itemId} );
        ItemInfo itemInfo = new Gson().fromJson(result , ItemInfo.class);
//        System.out.println("itemInfo:" + itemInfo);
        return new GenericResponse(itemInfo);
    }
    
    @Override
    public Response getAllItems() {
        String result = run(new String []{"method" , "getAllItems" , "prefix" , "asset"} );
        List<AllItemInfo> list = new Gson().fromJson(result , new TypeToken<ArrayList<AllItemInfo>>(){}.getType());
//        System.out.println("list:"+list);
        List<ItemInfo> itemInfos = list.stream().map(AllItemInfo::getRecord).collect(Collectors.toList());
        return new GenericResponse(itemInfos);
    }
    
    @Override
    public Response getItemHistory(String itemId) {
        String result = run(new String []{"method" , "getItemHistory" , "itemId" , itemId} );
//        System.out.println(result);
        List<ItemHistoryInfo> list = new Gson().fromJson(result , new TypeToken<ArrayList<ItemHistoryInfo>>(){}.getType());
        return new GenericResponse(list);
    }
    
    private String run(String[] args){
        String result = CommandHelper.run("node" , config.getQueryFileAddress() , args);
        int index = result.indexOf("Data:");
        return result.substring(index + 5);
//        return result;
    }
}
