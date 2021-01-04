package model;

import java.util.List;

import org.eclipse.jgit.revwalk.RevCommit;

public class PotentialRFC {
	private RevCommit commit;
	private int priority;
	private List<ChangedFile> normalJavaFiles;
	private List<ChangedFile> testCaseFiles;
	private List<PotentialTestCase> potentialTestcases;

	public PotentialRFC(RevCommit commit) {
		this.commit = commit;
	}

	public RevCommit getCommit() {
		return commit;
	}

	public void setCommit(RevCommit commit) {
		this.commit = commit;
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
