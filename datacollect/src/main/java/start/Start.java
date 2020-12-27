package start;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

import collector.PotentialBICDetector;
import collector.Provider;

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
