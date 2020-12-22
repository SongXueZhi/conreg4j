package model;

import java.util.List;

public class ChangedFile {
	private String newPath;
	private List<Method> methods;
	private int type;

	public ChangedFile(String newPath, int type) {
		this.newPath = newPath;
		this.type = type;
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
