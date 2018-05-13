package com.edu.nju.fabricservice.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:yangsanyang
 * Time:2018/5/10 10:18 PM.
 * Illustration:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentStatus {
    
    private double latitude;
    
    private double longitude;
    
    private String address;
    
    
    
}
