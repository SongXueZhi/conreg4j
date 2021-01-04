package model;

import java.util.List;

import org.eclipse.jgit.diff.Edit;

public class ChangedFile {
	private String newPath;
	private String oldPath;
	private List<Method> methods;
	private List<Edit> editList;
	private int type;

	public String getOldPath() {
		return oldPath;
	}

	public void setOldPath(String oldPath) {
		this.oldPath = oldPath;
	}

	public ChangedFile(String newPath) {
		this.newPath = newPath;
	}

	public ChangedFile(String newPath, int type) {
		this.newPath = newPath;
		this.type = type;
	}

	public List<Edit> getEditList() {
		return editList;
	}

	public void setEditList(List<Edit> editList) {
		this.editList = editList;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNewPath() {
		return newPath;
	}

	public void setNewPath(String newPath) {
		this.newPath = newPath;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

}
