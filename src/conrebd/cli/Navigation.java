/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd.cli;

import conrebd.ConReAction;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author knightsong
 */
public class Navigation {
    private  final  static String EXIT="exit"; 
    private  final  static String LIST="ls"; 
    private  final  static String HELP="help";
    private  final  static String GET="get";
    private  final  static String INFO="info";
    private  final  static String TEST="test";
    private  final  static String DIFF="diff";
    private  final  static String ADD="add";
    private  final  static String PULL="pull";
    private  final  static String REFRESH="pull";
   private  final  static String DROP="drop";
      private  final  static String ALL="all";
    private  final  static String DELETE="delete";
    
    
    private  final  static String FEATURES=LIST+"    --show all bug lise\n"
            +LIST+"grep <Key*>    --filter by keyword\n"
            +INFO+"<bugID>        --show bug Info,such as testcase,\n"
            + "                        diff in wc bic,commitcount,datediff\n"
            +INFO+"<bugID><version>    --show commit Info\n"
            +TEST+"<bugId> <version>    --test a case\n"
            +DIFF+"<bugId> <version1> <version2>    --diff two version\n"
            +GET+"<bugID>      --get source code of a bug\n"
            +ADD+"<params><sir name>    --add bug to database, If the project already exists,\n"
            + "                         you can use the -e parameter,otherwise use the -a parameter.\n"
            + "                         The default parameter is -a\n"
            +PULL+"<bugID><version>      --get a version source code"
            +DELETE+"<bugID>      --get a version source code"
            +REFRESH+"    --refresh Configs and DB\n"
            +DROP+"     --drop <sir name>\n"
            +HELP+"    --get all features\n"        
            +EXIT+"    --exit system";
   static ConReAction jctbe=new ConReAction();
    
    public static void main(String[] args) throws Exception {
        System.out.println("Set enviroment....");
        jctbe.setEnviroment();
        System.out.println("Set success");
        BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
        System.out.print("JaConReBE#:");
        String cmd=bf.readLine().trim();
        while (!cmd.equalsIgnoreCase(EXIT)) {
            navigation(cmd);
             System.out.print("JaConReBE#:");
            cmd=bf.readLine().trim();
        }
        
    }
    public static void navigation(String cmd) throws Exception {
        if (cmd.contains(HELP)) {
            StringBuilder sb=new StringBuilder();
            sb.append(FEATURES);
            System.out.println(sb);            
        }else if(cmd.contains(DIFF)){
            String[] params=cmd.split(" ");
            String bugID=params[1];
            String version1=params[2];
            String version2=params[3];
            System.out.println(jctbe.diff(bugID, version1, version2));
        }else if (cmd.contains(LIST)) {
             String[] params=cmd.split(" ");
             //ls grep pool*
             String[] buglist;
             if (params.length>2) {
                String pattern=params[2];
                buglist=jctbe.ls(pattern);
            }else{
             buglist=jctbe.ls();
             }  
             for (int i = 0; i <buglist.length; i++) {
                 System.out.println(buglist[i]);
            }
        }else if(cmd.contains(INFO)){
             String[] params=cmd.split(" ");
             String bugID=params[1];
             if (params.length>2) {
                String version=params[2];
             System.out.println(jctbe.info(bugID, version));
            }else{
                 System.out.println(jctbe.info(bugID));
             }          
        }else if(cmd.contains(GET)){
            String[] params=cmd.split(" ");
            String budD=params[1];
        }else if(cmd.contains(TEST)){
            String[] params=cmd.split(" ");
            String budD=params[1];
            String version=params[2];
            System.out.println(jctbe.test(budD, version));
            
        }else if(cmd.contains(PULL)){
            String[] params=cmd.split(" ");
            String budD=params[1];
            String version=params[2]; 
            
            System.out.println(jctbe.pullBug(budD, version)==true ? "download bug success":"download bug failed");          
        }else if(cmd.contains(REFRESH)){
        jctbe.refresh();
        }
        else{
            System.err.println("Unrecognized commands");
        }
        
    }
}
