/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd;

import conrebd.executor.Adminexec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author knightsong
 */
public class TestForAdmin {

    Adminexec exec = new Adminexec();
    String pathname = "/Users/knightsong/Desktop/data.csv";
    String currentFile = "";

    //jdk dbcp flink lucene derby groovy pool
    public void handleTask() throws Exception {
        try (BufferedReader bf = new BufferedReader(new FileReader(new File(pathname)))) {
            String line;
            while ((line = bf.readLine()) != null) {
                System.out.println(statictis(line));
            }
        }
    }

    public String statictis(String line) throws Exception {
        String[] ss = line.split(",");
        String projectName = ss[0];
        if (!projectName.equals(currentFile)) {
            currentFile = projectName;
            setDirectory("/Volumes/Samsung_T5/KnightSong/software/janconrebe/" + currentFile);
        }
        StringBuilder sb = new StringBuilder(currentFile);
        sb.append(",");
        sb.append(diffChunck(ss[1], ss[2])).append(",");
        sb.append(diffInChanges(ss[1], ss[2])).append(",");
        sb.append(diffInCommits(ss[1], ss[2])).append(",");
        sb.append(diffInTime(ss[1], ss[2]));
        return sb.toString();
    }

    public static void main(String[] args) throws IOException, Exception {
        TestForAdmin test = new TestForAdmin();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("admin#:");
        String cmd = bf.readLine().trim();
        test.navigation(cmd);
        while (!cmd.equalsIgnoreCase("exit")) {
            System.out.print("admin#:");
            cmd = bf.readLine().trim();
            test.navigation(cmd);
        }
    }

    public void setDirectory(String pathString) {
        exec.setDirectory(new File(pathString));

    }

    public String diffInChanges(String version1, String version2) throws Exception {
        // return exec.exec("git rev-list "+commitID2+".."+commitID1+" --count");
        //return exec.exec("git log --pretty=format:“%cd” "+commitID2+" -1");
        String diff1 = exec.exec("git diff " + version1 + " " + version2 + " --shortstat");

        return statictisChanggesLine(diff1);
    }
     public int diffChunck(String version1, String version2) throws Exception {
        // return exec.exec("git rev-list "+commitID2+".."+commitID1+" --count");
        //return exec.exec("git log --pretty=format:“%cd” "+commitID2+" -1");
        int diff1 = exec.exeChunck("git diff " + version1 + " " + version2);

        return diff1;
    }

    public String diffInCommits(String version1, String version2) throws Exception {

        String diff = exec.exec("git rev-list " + version1 + ".." + version2 + " --count");

        return diff;
    }

    public String diffInTime(String version1, String version2) throws Exception {

        String time1 = exec.exec("git show -s --format=%cd --date=short " + version1);
        String time2 = exec.exec("git show -s --format=%cd --date=short " + version2);
        String tt[] = time1.split("-");
        String tt1[] = time2.split("-");
        
        LocalDate date1=LocalDate.of( Integer.parseInt(tt[0]), Month.of(Integer.parseInt(tt[1])), Integer.parseInt(tt[2]));
        LocalDate date2=LocalDate.of(Integer.parseInt(tt1[0]), Month.of(Integer.parseInt(tt1[1])), Integer.parseInt(tt1[2]));
        return calculateTimeDifferenceByPeriod(date1, date2);
    }

    public  String calculateTimeDifferenceByPeriod(LocalDate oldDate, LocalDate newDate) {
        long l=oldDate.until(newDate,ChronoUnit.DAYS);
        return String.valueOf(l);
    }

    public void navigation(String cmd) throws Exception {
        if (cmd.contains("set")) {
            String[] params = cmd.split(" ");
            String pathString = params[1];
            setDirectory(pathString);

        } else if (cmd.contains("diff")) {
            String[] params = cmd.split(" ");
            String version1 = params[1];
            String version2 = params[2];
            System.out.println(diffChunck(version1, version2));
        }else{
        handleTask();
        }
    }

    public String statictisChanggesLine(String source) {
        String[] ss = source.split("changed,");
        String[] ss1 = ss[1].split(",");
        int sum = 0;
        for (int i = 0; i < ss1.length; i++) {
            try {
                String[] ss2 = ss1[i].split(" ");
                int tmp = Integer.parseInt(ss2[1]);
                sum += tmp;
            } catch (Exception e) {
            }
        }
        return String.valueOf(sum);
    }
    
}
