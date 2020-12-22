package model;

import java.util.List;

import org.eclipse.jgit.lib.ObjectId;

public class PotentialRFC {
	private ObjectId id;
	private int priority;
	private List<ChangedFile> normalJavaFiles;
	private List<ChangedFile> testCaseFiles;
	private List<PotentialTestCase> potentialTestcases;

	public PotentialRFC(ObjectId id) {
		this.id = id;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public List<ChangedFile> getNormalJavaFiles() {
		return normalJavaFiles;
	}

	public void setNormalJavaFiles(List<ChangedFile> normalJavaFiles) {
		this.normalJavaFiles = normalJavaFiles;
	}

	public List<ChangedFile> getTestCaseFiles() {
		return testCaseFiles;
	}

	public void setTestCaseFiles(List<ChangedFile> testCaseFiles) {
		this.testCaseFiles = testCaseFiles;
	}

	public List<model.PotentialTestCase> getPotentialTestcases() {
		return potentialTestcases;
	}

	public void setPotentialTestcases(List<model.PotentialTestCase> potentialTestcases) {
		this.potentialTestcases = potentialTestcases;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
