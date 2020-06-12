/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd;

import JavaBasedConfig.Configs;
import conrebd.docker.DockerExecutor;
import conrebd.domain.Bug;
import conrebd.domain.Entry;
import conrebd.executor.Execute;
import conrebd.utils.CodeUtils;
import conrebd.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author knightsong
 */
public class ConReAction {

    public static Execute exec = new Execute();
    public static DockerExecutor dexec = new DockerExecutor();
    LocalServer localServer = new LocalServer();

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

    public String info(String bugId, String version) throws Exception {
        String SIRName = ConReDB.getSirName(bugId).toLowerCase();
        File file = new File("database" + File.separator + SIRName);
        exec.setDirectory(new File(file.getAbsoluteFile() + File.separator + bugId + File.separator + version));
        return exec.exec("git show head~1  --name-only");
    }

    public String info(String bugId) throws Exception {
        Bug bug = ConReDB.getBug(bugId);
        String testcase = bug.getTestCase();
        String rootCause = bug.getRootCause();
        String docker = bug.getDocker();

        String SIRName = ConReDB.getSirName(bugId).toLowerCase();
        File file = new File("database" + File.separator + SIRName);
        exec.setDirectory(new File(file.getAbsoluteFile() + File.separator + bugId + File.separator + "fixed"));

        StringBuilder sb = new StringBuilder("---------bugInfo----------- \n");
        sb.append("TESTCASE:").append(testcase).append("\n");
        sb.append("ROOTCAUSE:").append(rootCause).append("\n");
        sb.append("DOCKER:").append(docker).append("\n").append("INFO DETAIL:\n");
        String wc = "", bfc = "";
        for (Entry entry : bug.getEntrys()) {
            String version = entry.getVersion();
            String commitID = entry.getCommit();
            if (version.equalsIgnoreCase("working")) {
                wc = commitID;
            }
            if (version.equalsIgnoreCase("fixed")) {
                bfc = commitID;
            }
            String commitTime = exec.exec("git log --pretty=format:“%cd” " + commitID + "~1" + " -1");
            sb.append(version).append(" ").append(commitID).append("~1").append(" ").append(commitTime)
                    .append("\n");
        }
        String diffCount = exec.exec("git rev-list " + wc + "~1" + ".." + bfc + "~1" + " --count");
        sb.append("Commits count between WC and BIC:").append(diffCount);
        return sb.toString();

    }

    public String test(String bugId, String version) throws Exception {
        Bug bug = ConReDB.getBug(bugId);
        Entry entry = bug.getEntry(version);
        String testcmd = entry.getTestCmd();
        String SIRName = ConReDB.getSirName(bugId).toLowerCase();
        File file = new File("database" + File.separator + SIRName);
        File target = new File(file.getAbsoluteFile() + File.separator + bugId + File.separator + version);
        if (!target.exists()) {
            System.out.println("Bug not in loacl,pull bug .....");
            pullBug(bugId, version);
        }
        exec.setDirectory(target);
        return "process end " + exec.execPrintln(testcmd);
    }

    public String diff(String bugId, String version1, String version2) throws Exception {
        Bug bug = ConReDB.getBug(bugId);
        String rootCause = bug.getRootCause();
        Entry entry1 = bug.getEntry(version1);
        Entry entry2 = bug.getEntry(version2);
        String commitID1 = entry1.getCommit();
        String commitID2 = entry2.getCommit();
        String SIRName = ConReDB.getSirName(bugId).toLowerCase();
        File file = new File("database" + File.separator + SIRName);
        File target = new File(file.getAbsoluteFile() + File.separator + bugId + File.separator + "fixed");
        exec.setDirectory(target);

        if (!target.exists()) {
            System.out.println("Bug not in loacl,pull bug .....");
            pullBug(bugId, "fixed");
        }

        System.out.println("-----------Diff--------------\nCHANGE FILES:");
        exec.execPrintln("git diff " + commitID1 + " " + commitID2 + " --stat");
        System.out.println("CHANGE DETAILS:");
        if ((version1.equals("working") && version2.equals("regression")) || (version1.equals("regression") && version2.equals("working"))) {
            List<String> causeSet = CodeUtils.getRootCause(rootCause, file.getAbsoluteFile() + File.separator + bugId);
            exec.execPrintlnC("git diff " + commitID1 + " " + commitID2, causeSet);
        }else{
           exec.execPrintln("git diff " + commitID1 + " " + commitID2);
        }
        return "";
    }

    public void setEnviroment() {
        Configs.refresh();
        String[] toolPaths = Configs.envPath.split(";");
        exec.setEnviroment(toolPaths);
    }

    public void refresh() {
        Configs.refresh();
        ConReDB.initDB();
    }
}
