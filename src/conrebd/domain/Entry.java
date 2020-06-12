/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd.domain;

/**
 *
 * @author knightsong
 */
public class Entry {

    private String version;
    private String testCmd;
    private String buildCmd;
    private String commit;
    /**
     * 
     * @param version
     * @param testCmd
     * @param buildCmd
     * @param source 
     */
    public Entry(String version, String testCmd, String source) {
        this.version = version;
        this.testCmd = testCmd;
        this.commit = source;
    }

    public String test(String cmdLine) {
        return null;
    }

    public String build(String cmdLine) {
        return null;
    }

    public String checkOut(String cmdLine) {
        return null;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the testCmd
     */
    public String getTestCmd() {
        return testCmd;
    }

    /**
     * @param testCmd the testCmd to set
     */
    public void setTestCmd(String testCmd) {
        this.testCmd = testCmd;
    }

    /**
     * @return the buildCmd
     */
    public String getBuildCmd() {
        return buildCmd;
    }

    /**
     * @param buildCmd the buildCmd to set
     */
    public void setBuildCmd(String buildCmd) {
        this.buildCmd = buildCmd;
    }

    /**
     * @return the commit
     */
    public String getCommit() {
        return commit;
    }

    /**
     * @param source the commit to set
     */
    public void setCommit(String source) {
        this.commit = source;
    }
}
