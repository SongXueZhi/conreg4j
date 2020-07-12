/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd;

import JavaBasedConfig.Configs;
import conrebd.docker.DockerExecutor;
import conrebd.domain.Bug;
import conrebd.domain.Constant;
import conrebd.domain.Entry;
import conrebd.executor.Execute;
import conrebd.utils.CodeUtils;
import conrebd.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.events.StartDocument;

import org.apache.commons.io.FileUtils;

/**
 *
 * @author knightsong
 */
public class ConReAction {

    public static Execute exec = new Execute();
    LocalServer localServer = new LocalServer();
    DockerServer dockerServer =new DockerServer();

    public String[] ls() {
        return ConReDB.bugIDsToArray();
    }

    public String[] ls(String pattern) {
        String[] tmp = ConReDB.bugIDsToArray();
        List<String> res = new ArrayList();
        for (int i = 0; i < tmp.length; i++) {
            String re = tmp[i];
            if (re.contains(pattern)) {
                res.add(re);
            }
        }
        return res.toArray(new String[res.size()]);
    }

    public boolean pullBug(String bugID, String version) throws Exception {

        Bug bug = ConReDB.getBug(bugID);
        String source = ConReDB.getSIR(ConReDB.getSirName(bugID)).getSource();
        Entry entry = bug.getEntry(version);
        String commitID = entry.getCommit();

        // SIR Direcotry sirPath
        String sirPath = Utils.checkFile(ConReDB.getSirName(bugID));

        //1. confirm meta code existing 
        File meta = Utils.checkContainsMeta(sirPath);
        if (meta == null) {
            System.out.println("Dowanload source code....");
            System.out.println("May need long time");
            // pull meta data
            int a = localServer.pull(sirPath, source);
            if (a == 0) {
                System.out.println("Download success");
                meta = new File(sirPath + File.separator + Utils.META_STRING);
            } else {
                System.out.println("Download failed");
            }

        }
        //2. meta have existed
        //bugID Files and copy meta to version directory
        File target = new File(sirPath + File.separator + bugID + File.separator + version);
        if (!target.exists()) {
            target.mkdirs();
        }
        //a.copy meta to version
        FileUtils.copyDirectory(meta, target);
        //b.checkout commitID
        localServer.checkout(target, commitID);
        return true;
    }
    
    
    public String pullBug(String bugID) throws Exception {
      return dockerServer.pullBug(bugID);
    }

    public String info(String bugId, String version) throws Exception {
        String SIRName = ConReDB.getSirName(bugId).toLowerCase();
        String commitId =ConReDB.getBug(bugId).getEntry(version).getOrignCommit();
        String cdCmd="cd "+File.separator+"home"+File.separator+SIRName;
        return dockerServer.run(cdCmd+";git show "+commitId+"  --name-only");
    }

    public String info(String bugId) throws Exception {
        Bug bug = ConReDB.getBug(bugId);
        String testCase = bug.getTestCases();
        String rootCause = bug.getRootCause();
        //     String rootFixed = bug.getRootFixed();
        String nature = bug.getNature();
        String SIRName = ConReDB.getSirName(bugId).toLowerCase();
 
        StringBuilder sb = new StringBuilder("---------bugInfo----------- \n");
        if (!nature.equals("")) {
            sb.append("Real bugID: ").append(nature).append("\n");
        }
        sb.append("TESTCASE:")
                .append(testCase).append("\n");
        sb.append("ROOTCAUSE:").append(rootCause).append("\n");
//        if (!rootFixed.equals("")) {
//            sb.append("ROOTFIXED:").append(rootFixed).append("\n");
//        }
        sb.append("INFO DETAIL:\n");
        String wc = "", bfc = "";
        String cdCmd="cd "+File.separator+"home"+File.separator+SIRName;
        for (Entry entry : bug.getEntrys()) {
            String version = entry.getVersion();
            String commitID = entry.getOrignCommit();
            if (version.equalsIgnoreCase("regression")) {
                wc = commitID;
            }
            if (version.equalsIgnoreCase("fixed")) {
                bfc = commitID;
            }
            
            String commitTime = dockerServer.run(cdCmd+";git log --pretty=format:“%cd” " + commitID + " -1");
            sb.append(version).append(" ").append(commitID).append(" ").append(commitTime)
                    .append("\n");
        }
        String diffCount = dockerServer.run(cdCmd+";git rev-list " + wc + ".." + bfc + " --count");
        sb.append("Commits count between BIC and BFC:").append(diffCount);
        return sb.toString();

    }

    public void test(String bugId, String version, boolean r) throws Exception {
        Bug bug = ConReDB.getBug(bugId);        
        Entry entry = bug.getEntry(version);
        String testcmd = entry.getTestCmd();
        String commitID=entry.getCommit();
        String sirName=ConReDB.getSirName(bugId);
        //Some cases can't reproducible,use -r to force
        if (r==true) {
			dockerServer.reproducible();
		}
         System.out.println(dockerServer.checkout(sirName, commitID));
         dockerServer.runTest(testcmd);
         dockerServer.endTest();

    }

    public String diff(String bugId, String version1, String version2) throws Exception {

        Bug bug = ConReDB.getBug(bugId);
        String rootCause = bug.getRootCause();

        String commitID1;
        String commitID2;
        Entry entry2 = bug.getEntry(version2);
        if (version1.equals("~1")) {
            commitID2 = entry2.getOrignCommit();
            commitID1 = commitID2 + "~1";
        } else {
            Entry entry1 = bug.getEntry(version1);
            commitID1 = entry1.getOrignCommit();
            commitID2 = entry2.getOrignCommit();
        }

        String SIRName = ConReDB.getSirName(bugId).toLowerCase();
        String cdCmd="cd /home"+File.separator+SIRName;

        System.out.println("-----------Diff--------------\nCHANGE FILES:");
        //>log.txt;cat log.txt;rm log.txt
        dockerServer.runPrintln(cdCmd+";git diff " + commitID1 + " " + commitID2 + " --stat");
        System.out.println("CHANGE DETAILS:");
        if ((version1.equals("working") && version2.equals("regression")) || (version1.equals("regression") && version2.equals("working"))) {
            List<String> causeSet = null;
//            		CodeUtils.getRootCause(rootCause, file.getAbsoluteFile() + File.separator + bugId);
            dockerServer.runPrintln(cdCmd+";git diff " + commitID1 + " " + commitID2);
        } else if ((version1.equals("~1") && version2.equals("fixed"))) {
            List<String> causeSet = CodeUtils.getRootFixed();
            dockerServer.runPrintln(cdCmd+";git diff " + commitID1 + " " + commitID2);
        } else {
        	dockerServer.runPrintln(cdCmd+";git diff " + commitID1 + " " + commitID2);
        }
        return "";
    }

    public void setEnviroment() {
        Configs.refresh();
        String[] toolPaths = Configs.envPath.split(";");
        exec.setEnviroment(toolPaths);
        dockerServer.setEnviroment(toolPaths);
    }

    public void refresh() {
        Configs.refresh();
        ConReDB.initDB();
    }

    public void add(String s) {
        exec.execPrintln("docker cp " + s + "conreg4j-java-plain:/scripts");
        System.out.println("Success");
    }
    
    public  String  startDocker() {
    	return exec.exec("docker start "+DockerExecutor.DOCKER_JAVA_PLAIN_CONTAINER_ID);    	
    }
    public String exit() {
    	
    	return exec.exec("docker stop "+DockerExecutor.DOCKER_JAVA_PLAIN_CONTAINER_ID);
    }
    
}
