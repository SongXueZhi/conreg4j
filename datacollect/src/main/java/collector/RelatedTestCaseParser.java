package collector;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

import model.Method;
import model.PotentialRFC;
import model.RelatedTestCase;
import model.TestFile;
import utils.CompilationUtil;

public class RelatedTestCaseParser {
	private Repository repo;

	RelatedTestCaseParser(Repository repo) {
		this.repo = repo;
	}

	public void parseTestCases(PotentialRFC pRFC) throws Exception {
		Iterator<TestFile> iterator = pRFC.getTestCaseFiles().iterator();
		while (iterator.hasNext()) {
			TestFile file = iterator.next();
			if (parse(file, pRFC).size() == 0) {
				iterator.remove();
			}
		}
	}

	public List<RelatedTestCase> parse(TestFile file, PotentialRFC pRFC) throws Exception {
		List<Edit> editList = file.getEditList();
		cleanEmpty(editList);
		List<Method> methodList = CompilationUtil.getAllMethod(getContextWithFile(pRFC.getCommit(), file.getNewPath()));
		List<RelatedTestCase> testCaseList = new LinkedList<>();
		if (justRepalceTypeEdit(editList)) {
			// TOFD 写运行脚本
		} else {
			testCaseList.addAll(getRelatedTestCase(editList, methodList));
		}
		return testCaseList;
	}

	public List<RelatedTestCase> getRelatedTestCase(List<Edit> editList, List<Method> methodList) {
		List<RelatedTestCase> result = new LinkedList<>();
		for (Edit edit : editList) {
			// 如果是insert暂时认为是插入了新的测试用例
			if (Edit.Type.INSERT == edit.getType()) {
				matchAll(edit, methodList, result);
			}
		}
		return result;
	}

	public void matchAll(Edit edit, List<Method> methods, List<RelatedTestCase> result) {
		for (Method method : methods) {
			match(edit, method, result);
		}
	}

	public void match(Edit edit, Method method, List<RelatedTestCase> result) {
		int editStart = edit.getBeginB();
		int editEnd = edit.getEndB();

		int methodStart = method.getStartLine();
		int methodStop = method.getStopLine();

		if (editStart <= methodStart && editEnd >= methodStop) {
			RelatedTestCase testCase = new RelatedTestCase();
			testCase.setType(RelatedTestCase.Type.Created);
			testCase.setMethod(method);
			result.add(testCase);
		}

	}

	public boolean justRepalceTypeEdit(List<Edit> editList) {
		for (Edit edit : editList) {
			if (edit.getType() == Edit.Type.INSERT) {
				return false;
			}
		}
		return true;
	}

	public void cleanEmpty(List<Edit> editList) {
		Iterator<Edit> iterator = editList.iterator();
		while (iterator.hasNext()) {
			Edit edit = iterator.next();
			// 如果是insert暂时认为是插入了新的测试用例
			if (Edit.Type.EMPTY == edit.getType()) {
				iterator.remove();
			}
		}
	}

	public String getContextWithFile(RevCommit commit, String filePath) throws Exception {
		RevWalk walk = new RevWalk(repo);
		RevTree revTree = commit.getTree();
		TreeWalk treeWalk = TreeWalk.forPath(repo, filePath, revTree);
		// 文件名错误
		if (treeWalk == null)
			return null;

		ObjectId blobId = treeWalk.getObjectId(0);
		ObjectLoader loader = repo.open(blobId);
		byte[] bytes = loader.getBytes();
		if (bytes != null)
			return new String(bytes);
		return null;

	}
}
