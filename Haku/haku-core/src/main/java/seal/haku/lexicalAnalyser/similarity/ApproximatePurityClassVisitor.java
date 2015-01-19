package seal.haku.lexicalAnalyser.similarity;

import java.util.HashSet;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * @Author Lisa
 * @Date: Jan 18, 2015
 * 
 *        APC Visitor marks all impure-prone methods and save to temporary
 *        files, for lexical analysis in the next step. In case it will run out
 *        of memory space when loading POS tagger, we separate syntactic
 *        analysis and lexical analysis.
 */
public class ApproximatePurityClassVisitor extends ASTVisitor {

	HashSet<String> methodList = new HashSet<String>();
	HashSet<String> fieldList = new HashSet<String>();

	public boolean visit(MethodDeclaration md) {
		String methodName = processMethodName(md);
		methodList.add(methodName);
		//FIXME: heuristic constructor filter
		if (methodName.charAt(0) - 'A' >= 0 && methodName.charAt(0) - 'Z' <= 0)
			return true;
		else {
				md.accept(new ApproximatePurityMethodVisitor(methodName,
						fieldList,md));
		}
		return true;
	}

	public boolean visit(FieldDeclaration fd) {

		fieldList.add(processFieldName(fd));
		return true;
	}

	public boolean isSuspiciousMethod(MethodDeclaration decleration) {
		for (String field : fieldList) {
			if (decleration.toString().contains(field + " ")) {
				return true;
			}
		}
		return false;
	}

	private String processFieldName(FieldDeclaration fd) {
		@SuppressWarnings("rawtypes")
		List fragment = fd.fragments();
		String fString = "";
		for (Object o : fragment)
			fString += o.toString().split("=")[0] + "\t";
		return fString;
	}

	private String processMethodName(MethodDeclaration md) {
		return md.getName().getIdentifier();
	}
}
