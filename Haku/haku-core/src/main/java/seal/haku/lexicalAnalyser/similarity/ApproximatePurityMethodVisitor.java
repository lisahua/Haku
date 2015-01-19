package seal.haku.lexicalAnalyser.similarity;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * @Author Lisa
 * @Date: Jan 18, 2015
 */
public class ApproximatePurityMethodVisitor extends ASTVisitor {
	private String output;
	private HashMap<String, String> fieldTypeMap = new HashMap<String, String>();
	private HashMap<String, String> variableTypeMap = new HashMap<String, String>();
	private HashSet<String> bugReason = new HashSet<String>();

	public ApproximatePurityMethodVisitor() {
	}

	public ApproximatePurityMethodVisitor(HashMap<String, String> fieldMap) {
		this.fieldTypeMap = fieldMap;
	}

	public boolean visit(ExpressionStatement es) {
		String suspiciousType = isSuspicious(es.getExpression()).trim();
		if (suspiciousType.length() > 1) {
			bugReason.add(suspiciousType);
		}
		return true;
	}

	public String getBug() {
		String bugs = "";
		for (String s : bugReason) {
			if (s.length() > 1)
				bugs += s + ",";
		}
		return bugs;
	}

	public boolean visit(VariableDeclarationStatement vd) {
		String vString = "";
		String tString = vd.getType().toString();

		for (Object o : vd.fragments()) {
			vString += o.toString().split("=")[0];
		}
		variableTypeMap.put(vString.trim(), tString.trim());
		return true;
	}

	private String isSuspicious(Expression exp) {
		if (exp instanceof MethodInvocation) {
			String invokeS = ((MethodInvocation) exp).toString();
			int docIndex = invokeS.indexOf('.');
			if (docIndex > 0) {
				char start = ((MethodInvocation) exp).getExpression()
						.toString().charAt(0);
				if (start > 'Z')
					return ",2,"
							+ isSuspicious(((MethodInvocation) exp)
									.getExpression()) +"."
							+ ((MethodInvocation) exp).getName().toString();
			} else
				return ",2," + invokeS;
		}
		// if (exp instanceof FieldAccess) {
		// return isSuspicious(((FieldAccess) exp).getExpression());
		// }
		if (exp instanceof Assignment) {
			String leftside = isSuspicious(((Assignment) exp).getLeftHandSide());
			if (leftside.length() > 0 && leftside.charAt(0) > 'Z')
				return ",1," + leftside;
			else
				return "";
		} else {
			String expS = exp.toString();
			int dotIndex = expS.indexOf(".");
			if (dotIndex > 0) {
				return replaceToType(expS.substring(0, dotIndex)) + "."
						+ replaceToType(expS.substring(dotIndex));
			} else {
				return replaceToType(expS);
			}
		}
	}

	private String replaceToType(String value) {
		if (fieldTypeMap.containsKey(value))
			return value;
		if (variableTypeMap.containsKey(value))
			return variableTypeMap.get(value);
		return "";
	}
}
