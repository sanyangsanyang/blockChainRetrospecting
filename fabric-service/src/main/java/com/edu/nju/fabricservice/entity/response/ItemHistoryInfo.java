package com.edu.nju.fabricservice.entity.response;

import com.edu.nju.fabricservice.entity.po.ItemInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:yangsanyang
 * Time:2018/5/12 10:21 AM.
 * Illustration:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemHistoryInfo {
    
    private String TxId;
    
    private ItemInfo Value;
}
