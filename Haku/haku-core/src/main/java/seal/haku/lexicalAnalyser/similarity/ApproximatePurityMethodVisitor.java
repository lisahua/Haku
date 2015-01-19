package seal.haku.lexicalAnalyser.similarity;

import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 * @Author Lisa
 * @Date: Jan 18, 2015
 */
public class ApproximatePurityMethodVisitor extends ASTVisitor {
	private String method;
	private HashSet<String> fieldSet;
private MethodDeclaration md;
	public ApproximatePurityMethodVisitor() {
	}

	public ApproximatePurityMethodVisitor(String method,
			HashSet<String> fieldSet,MethodDeclaration md) {
		this.method = method;
		this.fieldSet = fieldSet;
		this.md = md;
	}

	public boolean visit(ExpressionStatement es) {
		
		for (String field : fieldSet) {
			if (es.toString().contains(field)) {
				System.out.println("suspicious method " + method + " --> "
						+ es.toString());
			}
		}
		return true;
	}
	public boolean 	visit(MethodInvocation node)  {
		return true;
	}
	
}
