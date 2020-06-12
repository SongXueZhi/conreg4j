package conrebd.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class SingleExecuteTest {
	public static void main(String[] args) {
		/* Linux
		 * processBuilder.command("bash", "-c", "");
		 * Windows
		 * processBuilder.command("cmd.exe", "/c", ""); */
		
        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File("/Users/knightsong/NetBeansProjects/CuReBD/database/pool/pool-376/regression"));
        Map<String, String> map = pb.environment();
        
        String PATH = map.get("PATH") +
                File.pathSeparator +"/Users/knightsong/software/apache-maven-3.1.1/bin" +
                File.pathSeparator +"/Library/Java/JavaVirtualMachines/jdk1.8.jdk/Contents/Home/";
        map.put("PATH",PATH);
        
        String JAVA_HOME = "/Library/Java/JavaVirtualMachines/jdk1.8.jdk/Contents/Home/";
        map.put("JAVA_HOME",JAVA_HOME);
        
        //Debug
        System.out.println("START OF DEBUG");
        for(Map.Entry<String,String> entry: map.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("END OF DEBUG");

//        String commandToExecute = "pwd";
        String commandToExecute = "mvn -o -Dtest=TestGenericObjectPool#testNoInvalidateNPE -Drat.skip=true surefire:test";
        pb.command("bash", "-c", commandToExecute);
        
        try {
            Process process = pb.start();
            InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStr);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            if(exitCode != 0) {
                System.out.println("\nExited with error code : " + exitCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done.");
    }
}