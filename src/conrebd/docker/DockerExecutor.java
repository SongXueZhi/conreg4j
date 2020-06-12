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
public class DockerExecutor extends Executor{

    //.sh file is stored in this folder
    private final static String SCRIPTS_FOLDER = "scripts";

    private final static String DOCKER_BASH = "bash";

    private static String DOCKER_CONTAINER_ID = "9da42e4c7cd4";

    private final static String DOCKER_EXEC_BASED_CMD = "docker exec";

    private final static String DOCKER_EXEC_StART_CMD = "docker start";

    private final static String DOCKER_EXEC_COPY_CMD = "docker cp";

    public String run(String arg) {
        ProcessBuilder pb = new ProcessBuilder();
        Map<String, String> map = pb.environment();
        String PATH = map.get("PATH")
                + File.pathSeparator + "/Applications/Docker.app/Contents/Resources/bin";
        StringBuffer sb = new StringBuffer("test in docker...");
        map.put("PATH", PATH);
//        sb.append(exec(DOCKER_EXEC_StART_CMD + " " + DOCKER_CONTAINER_ID, pb));
        File file = new File(SCRIPTS_FOLDER + File.separator + arg);

//        exec(DOCKER_EXEC_COPY_CMD + " " + file.getAbsolutePath() + " " + DOCKER_CONTAINER_ID + ":" + SCRIPTS_FOLDER, pb);
//
//        String initCmd = DOCKER_EXEC_BASED_CMD + " " + DOCKER_CONTAINER_ID + " " + DOCKER_BASH + " " + SCRIPTS_FOLDER + File.separator + "init.sh";
//        sb.append(exec(initCmd, pb));

        String cmd = DOCKER_EXEC_BASED_CMD + " " + DOCKER_CONTAINER_ID + " " + DOCKER_BASH + " " + SCRIPTS_FOLDER + File.separator + arg;
        sb.append(exec(cmd, pb));

        return sb.toString();
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
                sb.append(line);
            }
        } catch (Exception ex) {

//exec("/usr/local/bin/docker images");
        }
        return sb.toString();
    }
}
