package gitwalk;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;

import collector.Provider;
import model.PotentialRFC;

public class TestBlame {
	PotentialRFC pRFC;
	Repository repo = null;
	Git git = null;

	@Before
	public void InitCommand() throws Exception {
		repo = new Provider().create(Provider.EXISITING).get("/Users/knightsong/Documents/project/microbat/.git");
		git = new Git(repo);
	}

	
	public void init() throws Exception {
		ObjectId id  = repo.resolve("29817ec53dd7570bf5e73a59fb6a0d618551bebe");
		pRFC =new PotentialRFC(id);
	}

}
