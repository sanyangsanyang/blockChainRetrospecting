package com.edu.nju.fabricservice.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:yangsanyang
 * Time:2018/5/11 9:48 AM.
 * Illustration:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteItemInfo {
    
    private String itemId;
    
    private boolean expire;
}
