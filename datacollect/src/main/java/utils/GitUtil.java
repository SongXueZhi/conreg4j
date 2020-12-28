package utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import model.ChangedFile;

public class GitUtil {
	public static List<ChangedFile> getLastDiffFiles(RevCommit commit, Repository repository) throws Exception {
		List<String> files = new ArrayList<>();
		ObjectId id = commit.getTree().getId();
		ObjectId oldId = commit.getParent(0).getTree().getId();
		try (ObjectReader reader = repository.newObjectReader()) {
			CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
			oldTreeIter.reset(reader, oldId);
			CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
			newTreeIter.reset(reader, id);
			// finally get the list of changed files
			try (Git git = new Git(repository)) {
				List<DiffEntry> diffs = git.diff().setNewTree(newTreeIter).setOldTree(oldTreeIter).call();
				for (DiffEntry entry : diffs) {
					files.add(entry.getNewPath());
				}
			}
		}
		return null;
	}

}
