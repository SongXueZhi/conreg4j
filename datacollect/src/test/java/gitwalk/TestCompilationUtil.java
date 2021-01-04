package gitwalk;

import java.io.FileInputStream;
import java.util.List;

import org.junit.Test;

import model.Method;
import utils.CompilationUtil;

public class TestCompilationUtil {

	@Test
	public void testGetMethodList() {
		String filePath = "D:\\document\\project\\Fruits\\src\\main\\java\\basket\\fruits\\Solution.java";
		FileInputStream fis = null;
		String fileContent = "";
		try {
			fis = new FileInputStream(filePath); // 内容是：abc
			StringBuilder sb = new StringBuilder();
			int temp = 0;
			// 当temp等于-1时，表示已经到了文件结尾，停止读取
			while ((temp = fis.read()) != -1) {
				sb.append((char) temp);
			}
			fileContent = sb.toString();
		} catch (Exception exc) {

		}
		List<Method> methodList = CompilationUtil.getAllMethod(fileContent);
		for (Method method : methodList) {
			String name = method.getSignature();
			int p = method.getStartLine();
			int q = method.getStopLine();
			System.out.println(name + " " + p + " --> " + q);
		}
	}

}
