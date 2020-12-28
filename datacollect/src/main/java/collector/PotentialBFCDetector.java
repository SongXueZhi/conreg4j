package collector;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.io.DisabledOutputStream;

import model.ChangedFile;
import model.PotentialRFC;
import model.PotentialTestCase;
import constant.FileType;
import constant.Priority;

public class PotentialBFCDetector {

	public List<PotentialRFC> detectPotentialBFC(Repository repo, Git git) throws Exception {
		// 定义需要记录的实验数据
		int countAll = 0, countLablecontainFix = 0, countNotOnlyTest = 0;
		int potenTestFix = 0;
		List<PotentialRFC> potentialRFCs = new ArrayList<>();
		// 获取一个库，库的获取可以是本地和使用gitclone从github中获取

		// 获取所有的commit，我们需要对所有的commit进行分析
		Iterable<RevCommit> commits = git.log().all().call();
		long time1 = System.currentTimeMillis();
		// 开始迭代每一个commit
		for (RevCommit commit : commits) {
			// 1)首先我们将记录所有的标题中包含fix的commti
			String message1 = commit.getShortMessage().toLowerCase();
			if (message1.contains("fix") || commit.getFullMessage().contains("Closes")) {
				countLablecontainFix++;
				/**
				 * 针对标题包含fix的commit我们进一步分析本次提交修改的文件路径 1）若所有路径中存在任意一个路径包含test相关的Java文件则我们认为本次提交
				 * 中包含测试用例。 2）若所有路径中除了测试用例还包含其他的非测试用例的文件则commit符合条件
				 **/
				List<ChangedFile> files = getLastDiffFiles(commit, repo);
				List<ChangedFile> testcaseFiles = getTestFiles(files);
				List<ChangedFile> normalJavaFiles = getNormalJavaFiles(files);
				if (testcaseFiles.size() > 0 && normalJavaFiles.size() > 0) {
					PotentialRFC pRFC = new PotentialRFC(commit.getId());
					pRFC.setTestCaseFiles(testcaseFiles);
					pRFC.setNormalJavaFiles(normalJavaFiles);
					pRFC.setPriority(Priority.high);
					potentialRFCs.add(pRFC);
					countNotOnlyTest++;
				} else if (justNormalJavaFile(files)) {
					/**
					 * 针对只标题只包含fix但是修改的文件路径中没有测试用例的提交 我们将在(c-3,c+3) 的范围内检索可能的测试用例 [TODO] 测试用例与测试方法对其
					 */
//					List<PotentialTestCase> pls = findTestCommit(commit, repo);
//					if (pls.size() > 0) {
//								PotentialRFC pRFC = new PotentialRFC(commit.getName());
//								pRFC.setNormalJavaFiles(normalJavaFiles);
//								pRFC.setPotentialTestcases(pls);
//								pRFC.setPriority(Priority.middle);
//								potenTestFix++;
//								potentialRFCs.add(pRFC);
//					}
				}
			}

			countAll++;
		}
		System.out.println("总共分析了" + countAll + "条commit\n" + "其中标题中包含Fix的commit有：" + countLablecontainFix
				+ "\n标题包含Fix且不仅只修改了test： " + countNotOnlyTest + "\n存在潜在测试用例的fix" + potenTestFix);
		System.out.println("总耗时 ：" + (System.currentTimeMillis() - time1) / 1000);
		return potentialRFCs;
	}

	/**
	 * 获取与父亲的差别
	 * 
	 * @param commit
	 * @param repository
	 * @return
	 * @throws Exception
	 */
	public List<ChangedFile> getLastDiffFiles(RevCommit commit, Repository repository) throws Exception {
		List<ChangedFile> files = new ArrayList<>();
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
					ChangedFile file = new ChangedFile(entry.getNewPath());
					file.setEditList(getEdits(entry));
				}
			}
		}
		return files;
	}

	public List<Edit> getEdits(DiffEntry entry) throws Exception {
		List<Edit> result = new ArrayList<Edit>();
		try (DiffFormatter diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE)) {
			FileHeader fileHeader = diffFormatter.toFileHeader(entry);
			List<? extends HunkHeader> hunkHeaders = fileHeader.getHunks();
			for (HunkHeader hunk : hunkHeaders) {
				result.addAll(hunk.toEditList());
			}
		}
		return result;

	}

	/**
	 * 任意两个diff之间的文件路径差别
	 * 
	 * @param oldCommit
	 * @param newCommit
	 * @param repository
	 * @return
	 * @throws Exception
	 */
	public List<String> getDiffFiles(RevCommit oldCommit, RevCommit newCommit, Repository repository) throws Exception {
		List<String> files = new ArrayList<>();
		ObjectId id = newCommit.getTree().getId();
		ObjectId oldId = oldCommit.getTree().getId();
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
		return files;
	}

	/**
	 * 判断是否只有测试文件，如果所有的修改文件路径都包含test，认为所有的 被修改文件只与测试用例有关
	 * 
	 * @param files
	 * @return
	 */
	public boolean justChangeTestFileOnly(List<String> files) {
		for (String str : files) {
			str = str.toLowerCase();
			// 如果有一个文件路径中不包含test
			// 便立即返回false
			String[] strings = str.toLowerCase().split("/");
			if (!(str.contains("test") && strings[strings.length - 1].contains(".java"))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取所有测试用例文件
	 * 
	 * @param files
	 * @return
	 */
	public List<ChangedFile> getTestFiles(List<ChangedFile> files) {
		List<ChangedFile> testFiles = new ArrayList<>();
		for (ChangedFile file : files) {
			String str = file.getNewPath();
			String[] strings = str.toLowerCase().split("/");
			if (strings.length > 0) {
				// 存在路径中包含test的Java文件，即测试用例
				if (str.contains("test") && strings[strings.length - 1].contains(".java")) {
					file.setType(constant.FileType.test);
					testFiles.add(file);
				}
			}
		}
		return testFiles;
	}

	/**
	 * 获取所有普通文件
	 */
	public List<ChangedFile> getNormalJavaFiles(List<ChangedFile> files) {
		List<ChangedFile> normalJavaFiles = new ArrayList<>();
		for (ChangedFile file : files) {
			String str = file.getNewPath();
			String[] strings = str.split("/");
			if (strings.length > 0) {
				// 存在非测试用例的Java文件
				if (!str.contains("test") && strings[strings.length - 1].contains(".java")) {
					file.setType(constant.FileType.normal);
					normalJavaFiles.add(file);
				}
			}
		}
		return normalJavaFiles;
	}

	/**
	 * 判断全部都是普通的Java文件
	 * 
	 * @param files
	 * @return
	 */
	public boolean justNormalJavaFile(List<ChangedFile> files) {
		for (ChangedFile file : files) {
			String str = file.getNewPath().toLowerCase();
			// 如果有一个文件路径中不包含test
			// 便立即返回false
			if (str.contains("test")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 如果一个程序中仅包含了fix但没有测试用例，那么我们将在(-3,+3)中检索是否有单独的测试用例被提交
	 * 
	 * @param commit
	 * @param repository
	 * @return
	 * @throws Exception
	 */
	public List<PotentialTestCase> findTestCommit(RevCommit commit, Repository repository) throws Exception {
		List<PotentialTestCase> potentialTestCases = new ArrayList<>();
		RevWalk revWalk = new RevWalk(repository);
		// 树结构 ^2 ^1 c ～1 ～2
		// c^1
		ObjectId newId1 = repository.resolve(commit.getName() + "~1");
		RevCommit newRev1 = null;
		if (newId1 != null) {
			newRev1 = revWalk.parseCommit(newId1);
			// 寻找是不是只有testcase的提交
			// 有则说明是潜在的testcase的提交
			if (justChangeTestFileOnly(getDiffFiles(commit, newRev1, repository))) {
				potentialTestCases.add(new PotentialTestCase(newRev1.getName(), 1));
			}
		}

		// c^2
		ObjectId newId2 = repository.resolve(commit.getName() + "~2");
		RevCommit newRev2 = null;
		if (newId1 != null && newId2 != null) {
			newRev2 = revWalk.parseCommit(newId2);
			// 是否只有测试用例
			if (justChangeTestFileOnly(getDiffFiles(newRev1, newRev2, repository))) {
				potentialTestCases.add(new PotentialTestCase(newRev2.getName(), 2));
			}
		}
		// c~1
		int num = commit.getParentCount();
		if (num > 1) {
			if (justChangeTestFileOnly(getDiffFiles(commit.getParent(1), commit.getParent(0), repository))) {
				potentialTestCases.add(new PotentialTestCase(commit.getParent(0).getName(), -1));
			}
			num--;
		}
		// c~2
		if (num > 1) {
			if (justChangeTestFileOnly(getDiffFiles(commit.getParent(2), commit.getParent(1), repository))) {
				potentialTestCases.add(new PotentialTestCase(commit.getParent(1).getName(), -2));
			}
			num--;
		}

		return potentialTestCases;
	}
}
