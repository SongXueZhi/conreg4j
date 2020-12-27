package collector;


import java.nio.channels.NonReadableChannelException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.BlameCommand;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import model.CandidateSet;
import model.ChangedFile;
import model.Method;
import utils.CompilationUtil;

public class Traverler {
	
	public void blame(ChangedFile file,Repository repo,ObjectId objectId) {
		try {
			BlameCommand blamer = new BlameCommand(repo);
			blamer.setStartCommit(objectId);
	        blamer.setFilePath(file.getNewPath());
	        BlameResult blame = blamer.call();
	        blameAllMethods(blame, file.getNewPath());
		} catch (Exception e) {
	
		}
		
	}

	public void blameAllMethods(BlameResult blame,String filePath) {
		List<Method> methods = CompilationUtil.getAllMethod(filePath);
		for (Method method : methods) {
			blameMethodScope(blame, method.getStartLine(), method.getStopLine());
		}
	}
	
	public void blameMethodScope(BlameResult blame,int startLine,int endLine) {
		CandidateSet candidateSet=new CandidateSet();
		List<RevCommit> commits=new ArrayList<>();
		for(int i=startLine;i<=endLine;i++) {
		commits.add(blame.getSourceCommit(i));
		}
		candidateSet.setCandidateList(commits);
	}
}
