package collector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.BlameCommand;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import model.BlameNode;
import model.ChangedFile;
import model.PotentialRFC;

public class Traverler {

	BlameCommand blamer;
	final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd HH:mm");

	public Traverler(Repository repo) {
		blamer = new BlameCommand(repo);
	}

	public List<BlameNode> getBlameGraph(PotentialRFC pRFC) throws Exception {
		blamer.setStartCommit(pRFC.getId());
		List<BlameNode> level1Nodes = new ArrayList<>();
		for (ChangedFile file : pRFC.getNormalJavaFiles()) {
			blamer.setFilePath(file.getNewPath());
			BlameResult result = blamer.call();
		
			for (Edit edit : file.getEditList()) {
				level1Nodes.addAll(blameEdit(edit, result));
			}
		}
		return level1Nodes;
	}

	public List<BlameNode> blameEdit(Edit edit,BlameResult result) {
		List<BlameNode> nodes = new ArrayList<>();
		if (edit.getType() != Edit.Type.REPLACE) {
			System.out.println("not replace ");
			return null;
		}
		for (int i =edit.getBeginA()+1; i <= edit.getEndA(); i++) {
			BlameNode node =new BlameNode();
			node.setCommit(result.getSourceCommit(i));
			node.setLine(result.getSourceLine(i));
			nodes.add(node);
		}
		return nodes;
	}

}
