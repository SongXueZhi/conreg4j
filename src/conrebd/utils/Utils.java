/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd.utils;

import java.io.File;

/**
 *
 * @author knightsong
 */
public class Utils {
    private final  static String FLEXI_STRING="database";
    public final  static String META_STRING="meta";
    
    public static  void checkBugSourceCode(String path){
    File file=new  File(path);
        if (!file.exists()) {
            System.out.println("Source code processing...");
            file.mkdirs();
        }
        
    }
    
   public static  String checkFile(String sirName){
       String path=FLEXI_STRING+File.separator+sirName;
        File file=new  File(path);
        if (!file.exists()) {
            file.mkdirs();
        }  
        return file.getAbsolutePath();
    }
   
     public static  File checkContainsMeta(String path){  
        File file =new File(path);
        File[] files=file.listFiles();
        if(files!=null){
            for (int i = 0; i < files.length; i++) {
                File file1 = files[i];
                if (file1.getName().contains(META_STRING)){ //2.meta existing                  
                    return  new  File(path+File.separator+META_STRING);
                }
            }
            // files not null but not contains meta
            return  null;
        }else{
            //files null
            return null;
        }
    }

    
}
