package com.edu.nju.fabricservice.service.impl;

import com.edu.nju.fabricservice.entity.po.*;
import com.edu.nju.fabricservice.entity.response.Response;
import com.edu.nju.fabricservice.service.InvokeService;
import com.edu.nju.fabricservice.utility.commmandlinetool.CommandHelper;
import com.edu.nju.fabricservice.utility.config.Config;
import org.springframework.stereotype.Service;

/**
 * Author:yangsanyang
 * Time:2018/5/11 9:21 PM.
 * Illustration:
 */
@Service
public class InvokeServiceImpl implements InvokeService {
    
    private Config config = Config.getInstance();
    
    @Override
    public Response addItem(ItemInfo itemInfo) {
        run(toArgs(itemInfo));
        return Response.defaultResponse();
    }
    
    @Override
    public Response changeItem(ChangeItemInfo changeItemInfo) {
        run(toArgs(changeItemInfo));
        return Response.defaultResponse();
    }
    
    @Override
    public Response expireItem(DeleteItemInfo deleteItemInfo) {
        run(toArgs(deleteItemInfo));
        return Response.defaultResponse();
    }
    
    private void run(String[]args){
        CommandHelper.run("node" , config.getInvokeFileAddress() , args);
    }
    
    private String[] toArgs(ItemInfo itemInfo){
    
        EnvironmentStatus environmentStatus = itemInfo.getEnvironmentStatus();
        FixedInfo fixedInfo = itemInfo.getFixedInfo();
        
        return new String[]{"method" , "addItem" , "itemId" , itemInfo.getItemId() , "latitude" , Double.toString(environmentStatus.getLatitude())
                            , "longitude" , Double.toString(environmentStatus.getLongitude()) , "address" , environmentStatus.getAddress()
                            , "name" , fixedInfo.getName() , "batchNum" , fixedInfo.getBatchNum() , "productionDate" , fixedInfo.getProductionDate()
                            , "expire" , Boolean.toString(itemInfo.isExpire())};
        
    }
    
    private String[] toArgs(ChangeItemInfo changeItemInfo){
        
        EnvironmentStatus environmentStatus = changeItemInfo.getEnvironmentStatus();
    
        return new String[]{"method" , "changeItem" , "itemId" , changeItemInfo.getItemId() , "latitude" , Double.toString(environmentStatus.getLatitude())
                , "longitude" , Double.toString(environmentStatus.getLongitude()) , "address" , environmentStatus.getAddress()};
    }
    
    private String[] toArgs(DeleteItemInfo deleteItemInfo){
        
        return new String[]{"method" , "expireItem" , "itemId" , deleteItemInfo.getItemId() , "expire" , Boolean.toString(deleteItemInfo.isExpire())};
    }
    
    
}
