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
public class Constant {

    public final static String[] VERSION = {"Working", "Regression", "Fixed"};
    public final static String XML_ROOT_LABEL = "root";
    public final static String XML_SIR_LABEL = "sir";
    public final static String XML_SIR_NAME_LABEL = "name";
    public final static String XML_SIR_SOURCE_LABEL = "source";
    public final static String XML_BUG_LABEL = "bug";
    public final static String XML_BUG_ID_LABEL = "id";
    public final static String XML_BUG_ROOT_CAUSE_LABEL = "rootcause";
    public final static String XML_BUG_ROOT_FIXED_LABEL = "rootfixed";
    public final static String XML_BUG_NATURE = "nature";
    public final static String XML_BUG_TESTCASE = "testcase";
    public final static String XML_BUG_DOCKER = "docker";
    public final static String XML_BUG_AUTHOR_LABEL = "author";
    public final static String XML_ENTRY_LABEL = "entry";
    public final static String XML_VRESION_LABEL = "version";
    public final static String XML_TEST_CMD_LABEL = "testcmd";
    public final static String XML_ORIGNCOMMIT_LABEL="orign";
//    public final static String XML_BUILD_CMD_LABEL = "buildcmd";
    public final static String XML_COMMIT_LABEL = "commit";
    public final static String WORKING_VERSION = VERSION[0];
    public final static String REGRESSION_VERSION = VERSION[1];
    public final static String FIXED_VERSION = VERSION[2];
    public final static String ILLEGAL_VERSION_INFO_EXCEPTION = "Not complete version Info in ";
    public final static String BUG_NOTHAVEN_INFO_EXCEPTION = "Bug not haven ";
    public  final  static  String DOCKER="docker";
    public final  static String LOCAL="local";

}
