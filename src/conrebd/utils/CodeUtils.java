/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author knightsong
 */
public class CodeUtils {
	private final static String CACHE_PATH="cache";
    
    public static List<String> getRootCause(String bugID) throws Exception{
    	String path=CACHE_PATH+File.separator+bugID+File.separator+bugID+"-regression";
        File file =new File(path);
        List<String> result =new ArrayList<>();
        FileReader  fr =new  FileReader(file);
        BufferedReader reader=new BufferedReader(fr);
        String line;
        while((line=reader.readLine())!=null){
        line=line.replaceAll("\n", "").trim();
            if (!"".equals(line) || line!=null) {
                     result.add(line);             
            }
        }
       return result;
    }

    
      public static List<String> getRootFixed(String bugID) throws Exception{
    	String path=CACHE_PATH+File.separator+bugID+File.separator+bugID+"-fixed";
        File file =new File(path);
        List<String> result =new ArrayList<>();
        FileReader  fr =new  FileReader(file);
        BufferedReader reader=new BufferedReader(fr);
        String line;
        while((line=reader.readLine())!=null){
        line=line.replaceAll("\n", "").trim();
            if (!"".equals(line) || line!=null) {
                     result.add(line);             
            }
        }
       return result;
      }
}
