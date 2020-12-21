package utils;

import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class CompilationUtil {
	public static CompilationUnit parseCompliationUnit(String fileContent){
		 ASTParser parser = ASTParser.newParser(AST.JLS13);  // handles JDK 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6
		 parser.setSource(fileContent.toCharArray());
		 // In order to parse 1.6 code, some compiler options need to be set to 1.6
		 Map<String, String> options = JavaCore.getOptions();
		 JavaCore.setComplianceOptions(JavaCore.VERSION_1_6, options);
		 parser.setCompilerOptions(options);
		 
		 CompilationUnit result = (CompilationUnit) parser.createAST(null);
		 return result;
	}
}
