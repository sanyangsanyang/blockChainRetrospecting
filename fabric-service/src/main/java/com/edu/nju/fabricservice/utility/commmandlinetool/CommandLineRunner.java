package com.edu.nju.fabricservice.utility.commmandlinetool;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Author:yangsanyang
 * Time:2018/5/10 4:20 PM.
 * Illustration:
 */
public class CommandLineRunner {
    
    protected static String run(CommandLine commandLine){
        String command = commandLine.getCommand();
        System.out.println("Running command:"+command);
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(command);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line ;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        String message = CommandLine.newCommandLineBuilder("cd /Users/yangsanyang/GoProjects/src/github.com/hyperledger/fabric-samples/fabcar && echo 'y' | ./stop.sh").build().run();
        System.out.println(message);
    }
}
