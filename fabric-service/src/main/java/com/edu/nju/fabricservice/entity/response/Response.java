package com.edu.nju.fabricservice.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:yangsanyang
 * Time:2018/5/11 9:04 PM.
 * Illustration:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    
    protected int code = 200;
    
    protected String err_msg = "";
    
    public static Response defaultResponse(){
        return new Response(200 , "");
    }
}
