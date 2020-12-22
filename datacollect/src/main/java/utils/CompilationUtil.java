package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.soap.Node;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import AST.MemberRetriever;
import model.Method;

public class CompilationUtil {
	public static CompilationUnit parseCompliationUnit(String fileContent) {
		ASTParser parser = ASTParser.newParser(AST.JLS13); // handles JDK 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6
		parser.setSource(fileContent.toCharArray());
		// In order to parse 1.6 code, some compiler options need to be set to 1.6
		Map<String, String> options = JavaCore.getOptions();
		JavaCore.setComplianceOptions(JavaCore.VERSION_1_6, options);
		parser.setCompilerOptions(options);

		CompilationUnit result = (CompilationUnit) parser.createAST(null);
		return result;
	}

	public static List<Method> getAllMethod(String filePath) {
		List<Method> methods = new ArrayList<>();
		MemberRetriever retriever = new MemberRetriever();
		CompilationUnit unit = parseCompliationUnit(filePath);
		unit.accept(retriever);
		List<ASTNode> methodNodes = retriever.getMemberList();
		for (ASTNode node : methodNodes) {
			MethodDeclaration methodDeclaration = (MethodDeclaration) node;
			String signature = methodDeclaration.getName().toString();
			int startLine = unit.getLineNumber(node.getStartPosition()) - 1;
			int endLine = unit.getLineNumber(node.getStartPosition() + node.getLength()) - 1;
			methods.add(new Method(signature, startLine, endLine));
		}
		return methods;
	}

}
