/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd.executor;

import conrebd.docker.DockerExecutor;
import conrebd.domain.Constant;

/**
 *
 * @author knightsong
 */
public class ExecutorManager {

    private static Execute loacalExecute;
    private static DockerExecutor dockerExecutor;

    public ExecutorManager() {
        loacalExecute = new Execute();
        dockerExecutor = new DockerExecutor();
    }

    public static Executor getExecutor(boolean isDocker) {
        if (isDocker == true) {
            return dockerExecutor;
        } else {
            return loacalExecute;
        }
    }
}
