package model;

import java.util.List;

public class PotentialRFC {
	private String id;
	private List<String> normalJavaFiles;
	private List<String> testCaseFiles;
	private List<PotentialTestCase> potentialTestcases;

	public PotentialRFC(String id){
		this.id=id;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getNormalJavaFiles() {
		return normalJavaFiles;
	}

	public void setNormalJavaFiles(List<String> normalJavaFiles) {
		this.normalJavaFiles = normalJavaFiles;
	}

	public List<String> getTestCaseFiles() {
		return testCaseFiles;
	}

	public void setTestCaseFiles(List<String> testCaseFiles) {
		this.testCaseFiles = testCaseFiles;
	}

	public List<model.PotentialTestCase> getPotentialTestcases() {
		return potentialTestcases;
	}

	public void setPotentialTestcases(List<model.PotentialTestCase> potentialTestcases) {
		this.potentialTestcases = potentialTestcases;
	}

}
