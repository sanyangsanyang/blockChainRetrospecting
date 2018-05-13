package com.edu.nju.fabricservice.utility.config;

import lombok.Data;
import org.yaml.snakeyaml.Yaml;

/**
 * Author:yangsanyang
 * Time:2018/5/11 8:17 PM.
 * Illustration:
 */
@Data
public class Config {
    
    private static Config config = Config.getInstance();
    
    private Config(){}
    
    public static Config getInstance(){
        if (config == null) {
//            Yaml yaml = new Yaml();
//            config = yaml.loadAs(Config.class.getResourceAsStream("/config.yml") , Config.class);
            config = new Config();
        }
        return config;
    }
    
    private String invokeFileAddress = "chaincode-invoker/invoke.js";
    
    private String queryFileAddress = "chaincode-invoker/query.js";
    
    private String registerFileAddress = "chaincode-invoker/registerUser.js";
    
    private String userFileAddress = "chaincode-invoker/hfc-key-store";
    
    private String startFileAddress = "scripts/startServer.sh";
    
    private String stopFileAddress = "scripts/stopServer.sh";
    
    private String restartFileAddress = "scripts/restartServer.sh";
    
    private String enrollFileAddress = "chaincode-invoker/enrollAdmin.js";
    
    public static void main(String[] args) {
        System.out.println(Config.getInstance().toString());
    }
}
