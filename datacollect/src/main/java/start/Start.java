package start;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

import gitwalk.PotentialBICDetector;
import gitwalk.Provider;

public class Start {

	public static void main(String[] args) throws Exception {
		try (Repository repo = new Provider().create(Provider.EXISITING).get()) {
			try (Git git = new Git(repo)) {
				PotentialBICDetector pBICDetector = new PotentialBICDetector();
				pBICDetector.detectPBIC(repo, git);
			}
		}
	}

}
