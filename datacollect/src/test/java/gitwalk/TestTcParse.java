package gitwalk;

import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;

import collector.PotentialBFCDetector;
import collector.Provider;
import collector.RelatedTestCaseParser;
import migration.TestcaseMigartion;
import model.PotentialRFC;
import model.RelatedTestCase;
import model.TestFile;

public class TestTcParse {
	PotentialRFC pRFC;
	Repository repo = null;
	Git git = null;

	@Before
	public void InitCommand() throws Exception {
		repo = new Provider().create(Provider.EXISITING).get("D:\\codecahe\\spring-framework\\.git");
		git = new Git(repo);
	}

	@Test
	public void testParse() throws Exception {
		PotentialBFCDetector pBFCDetector = new PotentialBFCDetector(repo, git);
		List<PotentialRFC> pRFCs = pBFCDetector.detectPotentialBFC();
		RelatedTestCaseParser rTCParser = new RelatedTestCaseParser(repo);
		PotentialRFC pRFC = pRFCs.get(0);
		rTCParser.parseTestCases(pRFC);
		List<TestFile> files = pRFC.getTestCaseFiles();
		for (TestFile file : files) {
			System.out.println(file.getNewPath());
			List<RelatedTestCase> tcList = file.getRelatedTestcaseList();
			if (tcList != null) {
				for (RelatedTestCase tc : tcList) {
					System.out.println(tc.getMethod().getSignature());
				}
			}
		}
		TestcaseMigartion tm = new TestcaseMigartion(repo);
		tm.testReduce(pRFC);

	}
}
