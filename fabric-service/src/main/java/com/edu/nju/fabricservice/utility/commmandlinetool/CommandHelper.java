package com.edu.nju.fabricservice.utility.commmandlinetool;

import com.edu.nju.fabricservice.utility.config.Config;
import org.springframework.stereotype.Component;

/**
 * Author:yangsanyang
 * Time:2018/5/11 8:30 PM.
 * Illustration:
 */
public class CommandHelper {
    
    public static String run (String action , String scriptName , String ...args){
        CommandLine.CommandLineBuilder builder ;
        if (action == null) {
            builder = CommandLine.newCommandLineBuilder(scriptName);
        } else {
            builder = CommandLine.newCommandLineBuilder("").appendScript(action , scriptName);
        }
        for (int index = 0 ; index < args.length ; index += 2) {
            builder.append(args[index] , args[index+1]);
        }
        return builder.build().run();
    }
}
