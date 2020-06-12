/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author knightsong
 */
public class Bug {


    private String bugID;
    private String author;
    private String testCase;
    private String rootCause;
    private String docker;

    private List<Entry> entrys;

    public Bug(String bugID, String author, String testcase,String rootCause,String docker,List<Entry> entrys) {
        this.bugID = bugID;
        this.author = author;
        this.entrys = entrys;
        this.docker=docker;
        this.rootCause=rootCause;
        this.testCase=testcase;
    }

    /**
     * @return the bugID
     */
    public String getBugID() {
        return bugID;
    }

    /**
     * @param bugID the bugID to set
     */
    public void setBugID(String bugID) {
        this.bugID = bugID;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the entrys
     */
    public List<Entry> getEntrys() {
        if (entrys == null) {
            return new ArrayList<>(Constant.VERSION.length);
        }
        return entrys;
    }

    /**
     * @param entrys the entrys to set
     */
    public void setEntrys(List<Entry> entrys) {
        this.entrys = entrys;
    }

    public Entry getEntry(String version) {
        for (Entry entry : entrys) {
            if (entry.getVersion().equalsIgnoreCase(version)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * @return the testCase
     */
    public String getTestCase() {
        return testCase;
    }

    /**
     * @param testCase the testCase to set
     */
    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }
        /**
     * @return the docker
     */
    public String getDocker() {
        return docker;
    }

    /**
     * @param docker the docker to set
     */
    public void setDocker(String docker) {
        this.docker = docker;
    }

    /**
     * @return the rootCause
     */
    public String getRootCause() {
        return rootCause;
    }

    /**
     * @param rootCause the rootCause to set
     */
    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }

}
