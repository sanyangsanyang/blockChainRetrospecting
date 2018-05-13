package com.edu.nju.fabricservice.service;

import com.edu.nju.fabricservice.entity.po.ItemInfo;
import com.edu.nju.fabricservice.entity.po.ChangeItemInfo;
import com.edu.nju.fabricservice.entity.po.DeleteItemInfo;
import com.edu.nju.fabricservice.entity.response.Response;

/**
 * Author:yangsanyang
 * Time:2018/5/11 8:57 PM.
 * Illustration:
 */
public interface InvokeService {
    
    Response addItem(ItemInfo itemInfo);
    
    Response changeItem(ChangeItemInfo changeItemInfo);
    
    Response expireItem(DeleteItemInfo deleteItemInfo);
    
    
}
