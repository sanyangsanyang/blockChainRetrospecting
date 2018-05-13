package com.edu.nju.fabricservice.service.impl;

import com.edu.nju.fabricservice.entity.response.GenericResponse;
import com.edu.nju.fabricservice.entity.response.Response;
import com.edu.nju.fabricservice.service.UserService;
import com.edu.nju.fabricservice.utility.commmandlinetool.CommandHelper;
import com.edu.nju.fabricservice.utility.config.Config;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author:yangsanyang
 * Time:2018/5/11 9:23 PM.
 * Illustration:
 */
@Service
public class UserServiceImpl implements UserService {
    
    private Config config = Config.getInstance();
    
    @Override
    public Response getAllUsers() {
        List<String> allUsers = getAllUsersName();
        return new GenericResponse(allUsers);
    }
    
    @Override
    public Response registerUser(String userName) {
        
        List<String> list = getAllUsersName();
        if (Objects.requireNonNull(list).contains(userName)) {
            return new Response(300 , "User already exists");
        }
        CommandHelper.run("node" , config.getRegisterFileAddress() , "name" , userName);
        return Response.defaultResponse();
    }
    
    private List<String> getAllUsersName(){
        String userAddress = config.getUserFileAddress();
        File file = new File(userAddress);
        if (file.isDirectory()){
            return Stream.of(Objects.requireNonNull(file.list())).filter(fileName -> !fileName.endsWith("priv") && !fileName.endsWith("pub") && !fileName.startsWith(".")).collect(Collectors.toList());
        }
        return null;
    }

}
