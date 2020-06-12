/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conrebd.utils;

import conrebd.domain.Bug;
import conrebd.domain.Constant;
import conrebd.domain.Entry;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author knightsong
 */
public class DataUtil {

    public final static String DBFILEPATH = "Metadata" + File.separator + "Database.xml";
    static Document doc = null;

    public static Document getDocument() {

        File bug_report = new File(DBFILEPATH);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            doc = documentBuilder.parse(bug_report);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;

    }

    public static void addBug(String sirName, Bug bug) {
        getDocument();
        //<sir>
        Element sirEle = getSIR(sirName);
        // if bug exist,need not add
        NodeList bugNodeList = sirEle.getElementsByTagName(Constant.XML_BUG_LABEL);
        if (containsBug(bugNodeList, bug.getBugID())) {
            return;
        }
        //<bug> add <bug> to <sir>
        Element bugEle = doc.createElement(Constant.XML_BUG_LABEL);
        sirEle.appendChild(bugEle);
        //<bug> child
        Element bugIDEle = doc.createElement(Constant.XML_BUG_ID_LABEL);
        bugIDEle.appendChild(doc.createTextNode(bug.getBugID()));
        Element authorEle = doc.createElement(Constant.XML_BUG_AUTHOR_LABEL);
        authorEle.appendChild(doc.createTextNode(bug.getAuthor()));
        bugEle.appendChild(bugIDEle);
        bugEle.appendChild(authorEle);

        for (Entry entry : bug.getEntrys()) {
            Element entryEle = doc.createElement(Constant.XML_ENTRY_LABEL);
            Element verionEle = doc.createElement(Constant.XML_VRESION_LABEL);
            verionEle.appendChild(doc.createTextNode(entry.getVersion()));
            Element testCmdEle = doc.createElement(Constant.XML_TEST_CMD_LABEL);
            testCmdEle.appendChild(doc.createTextNode(entry.getTestCmd()));
            Element sourceEle = doc.createElement(Constant.XML_COMMIT_LABEL);
            sourceEle.appendChild(doc.createTextNode(entry.getCommit()));
            entryEle.appendChild(sourceEle);
            entryEle.appendChild(testCmdEle);
            entryEle.appendChild(verionEle);
            bugEle.appendChild(entryEle);
        }
        write();
    }

    public static void write() {
        try {
            DOMSource domSource = new DOMSource(doc);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            File file = new File(DBFILEPATH);

            if (!file.exists()) {
                file.createNewFile();
            }
            StreamResult sr = new StreamResult(file);
            transformer.transform(domSource, sr);

        } catch (Exception ex) {
            Logger.getLogger(DataUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Element getSIR(String sirName) {
        Element root = (Element) doc.getElementsByTagName(Constant.XML_ROOT_LABEL).item(0);
        NodeList sirNodeList = root.getElementsByTagName(Constant.XML_SIR_LABEL);
        for (int i = 0; i < sirNodeList.getLength(); i++) {
            Element sir = (Element) sirNodeList.item(i);
            String name = sir.getElementsByTagName(Constant.XML_SIR_NAME_LABEL).item(0).getTextContent();
            if (name.equals(sirName)) {
                return sir;
            }
        }
        Element eleName = doc.createElement(Constant.XML_SIR_NAME_LABEL);
        eleName.appendChild(doc.createTextNode(sirName));
        Element sirEle = doc.createElement(Constant.XML_SIR_LABEL);
        sirEle.appendChild(eleName);
        root.appendChild(sirEle);
        return sirEle;
    }

    public static boolean containsBug(NodeList nodeList, String bugID) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element bug = (Element) nodeList.item(i);
            String id = bug.getElementsByTagName(Constant.XML_BUG_ID_LABEL).item(0).getTextContent();
            if (id.equals(bugID)) {
                return true;
            }
        }
        return false;
    }
}
