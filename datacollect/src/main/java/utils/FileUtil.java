package utils;

public class FileUtil {

	public void createdNewDirectory(String path) {

	}

	// /home/sxz/document/mmmmm-ddahkdak989/123.java
	public static String getDirectoryFromPath(String path) {
		return path.substring(0, path.lastIndexOf("/"));
	}

}
