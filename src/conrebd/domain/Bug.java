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
    private String nature;
    private String rootCause;
     private String rootFixed;
    private String testCases;
    private List<Entry> entrys;

    public Bug(String bugID, String author,String nature,String rootCause,String rootFixed,String testCases,List<Entry> entrys) {
        this.bugID = bugID;
        this.author = author;
        this.entrys = entrys;
        this.rootCause=rootCause;
        this.testCases=testCases;
        this.nature=nature;
        this.rootFixed=rootFixed;   
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

    /**
     * @return the testCases
     */
    public String getTestCases() {
        return testCases;
    }

    /**
     * @param testCases the testCases to set
     */
    public void setTestCases(String testCases) {
        this.testCases = testCases;
    }

    /**
     * @return the nature
     */
    public String getNature() {
        return nature;
    }

    /**
     * @param nature the nature to set
     */
    public void setNature(String nature) {
        this.nature = nature;
    }

       /**
     * @return the rootFixed
     */
    public String getRootFixed() {
        return rootFixed;
    }

    /**
     * @param rootFixed the rootFixed to set
     */
    public void setRootFixed(String rootFixed) {
        this.rootFixed = rootFixed;
    }
}
