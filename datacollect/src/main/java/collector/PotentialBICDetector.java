package collector;

import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

import model.ChangedFile;
import model.PotentialRFC;

public class PotentialBICDetector {

	public void detectPBIC(Repository repository, Git git) throws Exception {
		PotentialBFCDetector pBFCDetector = new PotentialBFCDetector(repository, git);
		List<PotentialRFC> pRFCs = pBFCDetector.detectPotentialBFC();

		for (PotentialRFC pRFC : pRFCs) {
			Traverler traverler = new Traverler(repository);
			traverler.getBlameGraph(pRFC);
		}

	}
}
