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
public class SIR {

    private  List<Bug> bugs = new ArrayList<>();
    private String SIRName;
    private String source;

    public SIR(String SIRName,String source) {
        this.SIRName = SIRName;
        this.source=source;
    }
    /**
     * @return the bugs
     */
    public List<Bug> getBugs() {
        return bugs;
    }

    /**
     * @param bugs the bugs to set
     */
    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }

    /**
     * @return the SIRName
     */
    public String getSIRName() {
        return SIRName;
    }

    /**
     * @param SIRName the SIRName to set
     */
    public void setSIRName(String SIRName) {
        this.SIRName = SIRName;
    }

    public void addBug(Bug bug) {
        this.bugs.add(bug);
    }

    public void removeBug(Bug bug) {
        getBugs().remove(bug);
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

}
