package model;

import java.util.List;

public class TestFile extends ChangedFile {
	List<RelatedTestCase> relatedTestcaseList;

	public List<RelatedTestCase> getRelatedTestcaseList() {
		return relatedTestcaseList;
	}

	public void setRelatedTestcaseList(List<RelatedTestCase> relatedTestcaseList) {
		this.relatedTestcaseList = relatedTestcaseList;
	}

	public TestFile(String newPath) {
		super(newPath);
	}
}
