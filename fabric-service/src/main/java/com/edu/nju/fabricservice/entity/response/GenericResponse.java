package com.edu.nju.fabricservice.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author:yangsanyang
 * Time:2018/5/12 8:51 AM.
 * Illustration:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse extends Response{
    
    private Object data;
    
    
}
