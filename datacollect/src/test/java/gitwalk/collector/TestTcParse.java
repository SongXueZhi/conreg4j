package gitwalk.collector;

import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;

import collector.PotentialBFCDetector;
import collector.Provider;
import collector.RelatedTestCaseParser;
import model.PotentialRFC;

public class TestTcParse {
	Repository repo = null;
	Git git = null;

	@Before
	public void InitCommand() throws Exception {
		repo = new Provider().create(Provider.EXISITING).get("/home/sxz/Documents/code/codecache/fastjson/.git");
		git = new Git(repo);
	}

	@Test
	public void testParse() throws Exception {
		PotentialBFCDetector pBFCDetector = new PotentialBFCDetector(repo, git);
		List<PotentialRFC> pRFCs = pBFCDetector.detectPotentialBFC();
		RelatedTestCaseParser rTCParser = new RelatedTestCaseParser(repo);
		rTCParser.parseTestSuite(pRFCs);

//		TestcaseMigartion tm = new TestcaseMigartion(repo);
//		tm.testReduce(pRFC);

	}
}
