/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd;

import java.io.File;

import com.sun.java.swing.plaf.gtk.resources.gtk_it;
import com.sun.java_cup.internal.runtime.virtual_parse_stack;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import conrebd.docker.DockerExecutor;

/**
 *
 * @author knightsong
 */
public class DockerServer {
	private static DockerExecutor dockerExecutor =new DockerExecutor();
	private  final static String DOCKER_HOME="home";
	
	public String checkout(String sirName,String commitID) {
		String cmd ="cd "+File.separator+DOCKER_HOME+File.separator+sirName+";git checkout "+commitID;
		return dockerExecutor.run(cmd);
	}
	
	public void runPrintln(String cmd) {
		 dockerExecutor.runPrintln(cmd);
	}
	public String run(String cmd) {
		return dockerExecutor.run(cmd);
	}
	
	public void setEnviroment(String[] toolPaths) {
		dockerExecutor.setEnviroment(toolPaths);		
	}
	
	public void runTest(String cmd) {
		dockerExecutor.runTest(cmd);
	}
	public void reproducible() {
		String cmd ="touch /home/rSwitch.lock";
		dockerExecutor.run(cmd);
	}
	
	public void endTest() {
		String cmd ="rm -rf /home/rSwitch.lock";
		dockerExecutor.run(cmd);
	}
	public String pullBug(String bugID) {
		String sirName=ConReDB.getSirName(bugID);
		String cdCmd="cd "+File.separator+"home"+File.separator+sirName;
		return dockerExecutor.run(cdCmd+";git checkout master;git pull origin master");
	}
	
	// ===============
	// pull 暂时不需要
	//===============
	
	
    
}
