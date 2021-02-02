package gitwalk.collector;

import java.io.PrintStream;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;

import collector.PotentialBFCDetector;
import collector.Provider;
import collector.RelatedTestCaseParser;
import collector.migrate.TestMigrator;
import model.ExperResult;
import model.PotentialRFC;

public class TestTcParse {
	Repository repo = null;
	Git git = null;
	long t1;
	long t2;

	@Before
	public void InitCommand() throws Exception {
		t1 = System.currentTimeMillis();
		repo = new Provider().create(Provider.EXISITING).get("/home/sxz/Documents/meta/fastjson/.git");
		git = new Git(repo);
	}

	@Test
	public void testParse() {
		try {

			PrintStream ps = new PrintStream("/home/sxz/Desktop/result.txt");
			System.setOut(ps);
			PotentialBFCDetector pBFCDetector = new PotentialBFCDetector(repo, git);
			List<PotentialRFC> pRFCs = pBFCDetector.detectPotentialBFC();
			RelatedTestCaseParser rTCParser = new RelatedTestCaseParser(repo);
			rTCParser.parseTestSuite(pRFCs);
			TestMigrator tm = new TestMigrator(repo);
			// tm.migrate(pRFCs.get(1));
			float i = 0;
			float j = (float) pRFCs.size();
			for (PotentialRFC pRfc : pRFCs) {
				i++;
				System.out.println(i / j + "%");
				tm.migrate(pRfc);
			}
			System.out.println("成功" + ExperResult.numSuc + "个，共" + j + "个: " + ExperResult.numSuc / j);
//		TestcaseMigartion tm = new TestcaseMigartion(repo);
//		tm.testReduce(pRFC);
		} catch (Exception ex) {

		}

	}
}
