/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd.docker;

import conrebd.executor.Executor;
import conrebd.utils.ColorConsole;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 *
 * @author knightsong
 */
public class DockerExecutor extends Executor {

    private static ProcessBuilder pb = new ProcessBuilder();
    // .sh file is stored in this folder
    private final static String SCRIPTS_FOLDER = "scripts";

    private final static String BASH = "bash";

    public static String DOCKER_JAVA_PLAIN_CONTAINER_ID = "e38";

    private final static String DOCKER_EXEC_BASED_CMD = "docker exec";

    public void runPrintln(String arg) {
        String cmd = DOCKER_EXEC_BASED_CMD + " " + "" + DOCKER_JAVA_PLAIN_CONTAINER_ID + " " + BASH + " -c '"
                + arg + "'";
        execPrintln(cmd, pb);
    }

    public void runPrintln(String arg, List<String> rootCause) {
        String cmd = DOCKER_EXEC_BASED_CMD + " " + "" + DOCKER_JAVA_PLAIN_CONTAINER_ID + " " + BASH + " -c '"
                + arg + "'";
        execPrintln(cmd, pb, rootCause);
    }

    public void runTest(String arg) {
        String cmd = DOCKER_EXEC_BASED_CMD + " " + DOCKER_JAVA_PLAIN_CONTAINER_ID + " " + "bash "
                + File.separator + SCRIPTS_FOLDER + File.separator + arg;
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

    public String execPrintln(String cmd, ProcessBuilder pb, List<String> rootCause) {
        StringBuffer sb = new StringBuffer();
        try {
            pb.command("bash", "-c", cmd);
            Process process = pb.start();
            InputStreamReader inputStr = new InputStreamReader(process.getInputStream());
            BufferedReader bufferReader = new BufferedReader(inputStr);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                if (checkRootCause(line, rootCause)) {
                    ColorConsole.println(line);
                } else {
                    System.out.println(line);
                }

            }
        } catch (Exception ex) {
        }
        return sb.toString();
    }

    public String run(String arg) {
        String cmd = DOCKER_EXEC_BASED_CMD + " " + "" + DOCKER_JAVA_PLAIN_CONTAINER_ID + " " + BASH + " -c '"
                + arg + "'";
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

    private boolean checkRootCause(String line, List<String> causeList) {
        String tmp = line.replace('+', ' ').replace('-', ' ').replace(" ", "");
        for (String s : causeList) {
            s = s.replace(" ", "");
            if (tmp.contains(s) && s.toCharArray().length > 3 && !tmp.contains("/**")) {
                return true;
            }
        }
        return false;
    }
}
