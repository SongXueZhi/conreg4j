/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd;

import conrebd.domain.Bug;
import conrebd.domain.Constant;
import conrebd.domain.Entry;
import conrebd.domain.SIR;
import conrebd.utils.DataUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Source;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author knightsong
 */
public class ConReDB {

    public static List<SIR> sirs;
    private static Map<String, String> sirBugMap;
    

    public static void initDB() {
        Document document = DataUtil.getDocument();
        NodeList sirList = document.getElementsByTagName(Constant.XML_SIR_LABEL);
        int sirLength = sirList.getLength();
        sirs = new ArrayList<>(sirLength);
        for (int i = 0; i < sirLength; i++) {
            Element sirItem = (Element) sirList.item(i); //一个sir
            String sirName = sirItem.getElementsByTagName(Constant.XML_SIR_NAME_LABEL).item(0).getTextContent();
            String source = sirItem.getElementsByTagName(Constant.XML_SIR_SOURCE_LABEL).item(0).getTextContent();
            SIR sir = new SIR(sirName,source);
            //一个sir对应多个bug
            NodeList bugList = sirItem.getElementsByTagName(Constant.XML_BUG_LABEL);
            for (int j = 0; j < bugList.getLength(); j++) {
                Element bugItem = (Element) bugList.item(j);
                String bugID = bugItem.getElementsByTagName(Constant.XML_BUG_ID_LABEL).item(0).getTextContent();
                String author = bugItem.getElementsByTagName(Constant.XML_BUG_AUTHOR_LABEL).item(0).getTextContent();
                String docker = bugItem.getElementsByTagName(Constant.XML_BUG_DOCKER).item(0).getTextContent();
                String testCase = bugItem.getElementsByTagName(Constant.XML_BUG_TESTCASE).item(0).getTextContent();
                String rootCause=bugItem.getElementsByTagName(Constant.XML_BUG_ROOT_CAUSE_LABEL).item(0).getTextContent();
                NodeList entryList = bugItem.getElementsByTagName(Constant.XML_ENTRY_LABEL);
                if (entryList.getLength() < Constant.VERSION.length) {
                    try {
                        throw new Exception(Constant.ILLEGAL_VERSION_INFO_EXCEPTION + bugID);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                List<Entry> entrys = new ArrayList<>(Constant.VERSION.length);
                for (int k = 0; k < Constant.VERSION.length; k++) {
                    Element entryItem = (Element) entryList.item(k);
                    String testCmd = entryItem.getElementsByTagName(Constant.XML_TEST_CMD_LABEL).item(0).getTextContent();
//                    String buildCmd = entryItem.getElementsByTagName(Constant.XML_BUILD_CMD_LABEL).item(0).getTextContent();
                    String commit = entryItem.getElementsByTagName(Constant.XML_COMMIT_LABEL).item(0).getTextContent();
                    String version = entryItem.getElementsByTagName(Constant.XML_VRESION_LABEL).item(0).getTextContent();
                    entrys.add(new Entry(version, testCmd, commit));
                }
                sir.addBug(new Bug(bugID, author,testCase,rootCause,docker,entrys));
            }
            sirs.add(sir);
        }
        initSirBugMap();
    }

    private static void initSirBugMap() {
        sirBugMap = new HashMap<>();
        sirs.forEach((sir) -> {
            sir.getBugs().forEach((bug) -> {
                sirBugMap.put(bug.getBugID(), sir.getSIRName());
            });
        });
    }

    public static String getSirName(String bugID) {
        return sirBugMap.get(bugID);
    }

    public static String[] bugIDsToArray() {
        List<Bug> bugs = new ArrayList<>();
        sirs.forEach((sir) -> {
            bugs.addAll(sir.getBugs());
        });
        String[] bugIDs = new String[bugs.size()];
        int i = 0;
        for (Bug bug : bugs) {
            bugIDs[i] = bug.getBugID();
            ++i;
        }
        return bugIDs;
    }

    public static boolean contains(String sirName) {
        sirName = sirName.toLowerCase().trim();
        for (SIR sir : sirs) {
            if (sir.getSIRName().equals(sirName)) {
                return true;
            }
        }
        return false;
    }

    public static SIR getSIR(String sirName) {

        for (SIR sir : sirs) {
            if (sir.getSIRName().equals(sirName)) {
                return sir;
            }
        }
        return null;
    }

    public static Bug getBug(String bugID) throws Exception {

        for (SIR sir : sirs) {
            for (Bug bug : sir.getBugs()) {
                if (bug.getBugID().equals(bugID)) {
                    return bug;
                }
            }
        }
       throw new Exception(Constant.BUG_NOTHAVEN_INFO_EXCEPTION);
    }
}
