package model;

public class Method {

	private String signature;
	private int startLine;
	private int stopLine;

	public Method(String signature) {
		this.signature = signature;
	}

	public Method(String signature, int startLine, int stopLine) {
		this.signature = signature;
		this.startLine = startLine;
		this.stopLine = stopLine;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getStartLine() {
		return startLine;
	}

	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}

	public int getStopLine() {
		return stopLine;
	}

	public void setStopLine(int stopLine) {
		this.stopLine = stopLine;
	}

}
