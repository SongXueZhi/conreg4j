/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 *
 * @author knightsong
 */
public class Adminexec {
    


    public final static String OS_WINDOWS = "windows";
    public final static String OS_MAC = "mac";
    public final static String OS_UNIX = "unix";

    ProcessBuilder pb = new ProcessBuilder();

    public void setEnviroment(String args[]) {

        Map<String, String> map = pb.environment();
        StringBuilder PATH = new StringBuilder(map.get("PATH"));
        for (String arg : args) {
            PATH.append(File.pathSeparator).append(arg);
        }
        map.put("PATH", PATH.toString());
    }

    public void setDirectory(File file) {
        pb.directory(file);
    }

    public String exec(String cmd) {
        StringBuilder builder = new StringBuilder();
        try {
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.equals(OS_WINDOWS)) {
                pb.command("cmd.exe", "/c", cmd);
            } else {
                pb.command("bash", "-c", cmd);
            }
            Process process = pb.start();
            InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStr);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
    
     public int exeChunck(String cmd) {
         int a=0;
        try {
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.equals(OS_WINDOWS)) {
                pb.command("cmd.exe", "/c", cmd);
            } else {
                pb.command("bash", "-c", cmd);
            }
            Process process = pb.start();
            InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStr);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                if (checkChunkHead(line)) {
                    a++;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return a;
    }
     boolean checkChunkHead(String line){
         String[] ss=line.split(" ");
         if(ss.length>=4&&ss[0].equals("@@")&&ss[3].equals("@@")&&ss[1].contains("-")&&ss[2].contains("+")) {
             return true;
         }
         return false;
     }
}

    

