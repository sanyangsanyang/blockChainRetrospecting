package com.edu.nju.fabricservice.utility.commmandlinetool;

import lombok.Data;

/**
 * Author:yangsanyang
 * Time:2018/5/10 4:04 PM.
 * Illustration:
 */
@Data
public class CommandLine {
    
    private String command;
    
    private CommandLine (String command){
        this.command = command;
    }
    
    protected static CommandLineBuilder newCommandLineBuilder (String primitiveCommand){
        return new CommandLineBuilder(primitiveCommand);
    }
    
    
    static class CommandLineBuilder {
        
        private StringBuilder builder;
        
        private char space = ' ';
        
        private CommandLineBuilder (String primitiveCommand){
            builder = new StringBuilder(primitiveCommand);
        }
        
        CommandLineBuilder append (String option , String parameter){
            builder.append(space).append("-").append(option)
                    .append(space).append(parameter);
            return this;
        }
        
        CommandLineBuilder appendScript (String action , String scriptName){
            builder.append(action).append(space).append(scriptName);
            return this;
        }
        
        CommandLine build(){
            return new CommandLine(builder.toString());
        }
    }
    
    public String run(){
        return CommandLineRunner.run(this);
    }
}
