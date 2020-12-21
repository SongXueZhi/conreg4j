package gitwalk;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

public class PotentialBICDetector {
	
	public void detectPBIC(Repository repository,Git git) throws Exception {
		PotentialBFCDetector pBFCDetector = new PotentialBFCDetector();
		pBFCDetector.detectPotentialBFC(repository,git);
				
		
	}

}
