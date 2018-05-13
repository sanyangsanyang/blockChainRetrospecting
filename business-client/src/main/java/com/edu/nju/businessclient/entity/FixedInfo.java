package com.edu.nju.businessclient.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:yangsanyang
 * Time:2018/5/11 9:36 AM.
 * Illustration:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedInfo {
    
    private String name;
    
    private String batchNum;
    
    private String productionDate;
    
}
