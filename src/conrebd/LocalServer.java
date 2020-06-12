/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd;

import conrebd.executor.Execute;
import conrebd.utils.Utils;
import java.io.File;

/**
 *
 * @author knightsong
 */
public class LocalServer {
     static Execute exec;
     private  final static String GITCLONE="git clone ";
     private  final static String CHECKOUT="git checkout -f  ";

    public LocalServer() {
        exec=new Execute();
    }
    
    public int  pull(String path,String url){
    exec.setDirectory(new File(path));
    exec.execPrintln("export ALL_PROXY=socks5://127.0.0.1:1086");
    int a= exec.execPrintln(GITCLONE+url+" meta");
    return  a;
    }
    
    public int  checkout(File file,String commit){
    exec.setDirectory(file);
    int a= exec.execPrintln(CHECKOUT+commit);
    return  a;
    }
}
