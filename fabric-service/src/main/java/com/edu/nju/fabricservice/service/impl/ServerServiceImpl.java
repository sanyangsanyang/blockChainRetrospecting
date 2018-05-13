package com.edu.nju.fabricservice.service.impl;

import com.edu.nju.fabricservice.entity.response.Response;
import com.edu.nju.fabricservice.service.ServerService;
import com.edu.nju.fabricservice.utility.commmandlinetool.CommandHelper;
import com.edu.nju.fabricservice.utility.config.Config;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Author:yangsanyang
 * Time:2018/5/12 7:21 AM.
 * Illustration:
 */
@Service
public class ServerServiceImpl implements ServerService {
    
    private Config config = Config.getInstance();
    
    @Override
    public Response startServer() {
        String result = CommandHelper.run(null , config.getStartFileAddress());
        System.out.println(result);
        enrollAdminAndUser();
        return Response.defaultResponse();
    }
    
    @Override
    public Response stopServer() {
        String result = CommandHelper.run(null , config.getStopFileAddress());
        System.out.println(result);
        return Response.defaultResponse();
    }
    
    @Override
    public Response restartServer() {
        String result = CommandHelper.run(null , config.getRestartFileAddress());
        System.out.println(result);
        enrollAdminAndUser();
        return Response.defaultResponse();
    }
    
    private void enrollAdminAndUser(){
        System.out.println("Cleaning previous certificate……");
        File f = new File(config.getUserFileAddress());
        if (f.exists()) {
            if (f.isDirectory()) {
                for (File file : f.listFiles())
                    file.delete();
            }
            f.delete();
        }
        System.out.println("Directory is removed!");
        CommandHelper.run("node" , config.getEnrollFileAddress());
        CommandHelper.run("node" , config.getRegisterFileAddress() , "name" , "user1");
    }
    
    public static void main(String[] args) {
        new ServerServiceImpl().restartServer();
    }
    
//    public static void main(String[] args) {
//        ServerService serverService = new ServerServiceImpl();
//        long startTime = System.currentTimeMillis();
//        serverService.restartServer();
//        long endTime = System.currentTimeMillis();
//        System.out.println((endTime-startTime)/1000+"s");
//    }
}
