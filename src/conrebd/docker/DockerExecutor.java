/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd.docker;

import conrebd.executor.Executor;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Map;

/**
 *
 * @author knightsong
 */
public class DockerExecutor extends Executor {
    private static ProcessBuilder pb =new ProcessBuilder();
	// .sh file is stored in this folder
	private final static String SCRIPTS_FOLDER = "scripts";

	private final static String BASH="bash";

	public static String DOCKER_JAVA_PLAIN_CONTAINER_ID = "conreg4j-java-plain";

	private final static String DOCKER_EXEC_BASED_CMD = "docker exec";

	public void runPrintln(String arg) {
		String cmd = DOCKER_EXEC_BASED_CMD + " " +""+ DOCKER_JAVA_PLAIN_CONTAINER_ID + " " +BASH+" -c '"
				 + arg+"'";
		execPrintln(cmd, pb);
	}
	
	public void runTest(String arg) {
		String cmd = DOCKER_EXEC_BASED_CMD +" "+ DOCKER_JAVA_PLAIN_CONTAINER_ID + " "+"bash " 
				+ File.separator+SCRIPTS_FOLDER + File.separator + arg;
		execPrintln(cmd, pb);
	}

	public String execPrintln(String cmd, ProcessBuilder pb) {
		StringBuffer sb = new StringBuffer();
		try {
			pb.command("bash", "-c", cmd);
			Process process = pb.start();
			InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
			BufferedReader bufferReader = new BufferedReader(inputStr);
			String line;
			while ((line = bufferReader.readLine()) != null) {
				System.out.println(line);

			}
		} catch (Exception ex) {
		}
		return sb.toString();
	}
	public String run(String arg) {
		String cmd = DOCKER_EXEC_BASED_CMD + " " +""+ DOCKER_JAVA_PLAIN_CONTAINER_ID + " " +BASH+" -c '"
				 + arg+"'";
		return exec(cmd, pb);
	}
	public String exec(String cmd, ProcessBuilder pb) {
		StringBuffer sb = new StringBuffer();
		try {
			pb.command("bash", "-c", cmd);
			Process process = pb.start();
			InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
			BufferedReader bufferReader = new BufferedReader(inputStr);
			String line;
			while ((line = bufferReader.readLine()) != null) {				
				sb.append(line).append("\n");
			}
		} catch (Exception ex) {
		}
		return sb.toString();
	}
	
	 public void setEnviroment(String args[]) {

	        Map<String, String> map = pb.environment();
	        StringBuilder PATH = new StringBuilder(map.get("PATH"));
	        for (int i = 1; i < args.length; i++) {
	            PATH.append(File.pathSeparator).append(args[i]);
	        }
	        map.put("PATH", PATH.toString());
	    }
}
