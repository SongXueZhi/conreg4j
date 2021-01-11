package collector;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.Repository;

import model.Method;
import model.PotentialRFC;
import model.RelatedTestCase;
import model.TestFile;
import utils.CompilationUtil;
import utils.GitUtil;

public class RelatedTestCaseParser {
	private Repository repo;

	public RelatedTestCaseParser(Repository repo) {
		this.repo = repo;
	}

	public void parseTestCases(PotentialRFC pRFC) throws Exception {
		Iterator<TestFile> iterator = pRFC.getTestCaseFiles().iterator();
		while (iterator.hasNext()) {
			TestFile file = iterator.next();
			String code = GitUtil.getContextWithFile(repo, pRFC.getCommit(), file.getNewPath());
			if (!isTestcase(code)) {
				iterator.remove();
				System.out.println(file.getNewPath() + ": 被移除");
			}
			List<RelatedTestCase> rcLsit = parse(file, pRFC, code);
			if (rcLsit.size() == 0) {
				iterator.remove();
			} else {
				file.setRelatedTestcaseList(rcLsit);
			}
		}
	}

	private List<RelatedTestCase> parse(TestFile file, PotentialRFC pRFC, String code) throws Exception {
		List<Edit> editList = file.getEditList();
		cleanEmpty(editList);
		List<Method> methodList = CompilationUtil.getAllMethod(code);
		List<RelatedTestCase> testCaseList = new LinkedList<>();
		if (justRepalceTypeEdit(editList)) {
			// TODO 写运行脚本
		} else {
			testCaseList.addAll(getRelatedTestCase(editList, methodList));
		}
		return testCaseList;
	}

	private List<RelatedTestCase> getRelatedTestCase(List<Edit> editList, List<Method> methodList) {
		List<RelatedTestCase> result = new LinkedList<>();
		for (Edit edit : editList) {
			// 如果是insert暂时认为是插入了新的测试用例
			if (Edit.Type.INSERT == edit.getType()) {
				matchAll(edit, methodList, result);
			} else {

			}
		}
		return result;
	}

	private void matchAll(Edit edit, List<Method> methods, List<RelatedTestCase> result) {
		for (Method method : methods) {
			match(edit, method, result);
		}
	}

	private void match(Edit edit, Method method, List<RelatedTestCase> result) {
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

	private boolean justRepalceTypeEdit(List<Edit> editList) {
		for (Edit edit : editList) {
			if (edit.getType() == Edit.Type.INSERT) {
				return false;
			}
		}
		return true;
	}

	private void cleanEmpty(List<Edit> editList) {
		Iterator<Edit> iterator = editList.iterator();
		while (iterator.hasNext()) {
			Edit edit = iterator.next();
			// 如果是insert暂时认为是插入了新的测试用例
			if (Edit.Type.EMPTY == edit.getType()) {
				iterator.remove();
			}
		}
	}

	private boolean isTestcase(String code) {
		if (code.contains("junit") || code.contains("@est")) {
			return true;
		}
		return false;
	}

}
