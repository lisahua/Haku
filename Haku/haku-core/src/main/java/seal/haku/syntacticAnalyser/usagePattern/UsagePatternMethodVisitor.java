package seal.haku.syntacticAnalyser.usagePattern;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * @Author Lisa
 * @Date: Jan 18, 2015
 */
public class UsagePatternMethodVisitor extends ASTVisitor {
	private HashMap<String, String> fieldTypeMap = new HashMap<String, String>();
	private HashMap<String, String> variableTypeMap = new HashMap<String, String>();
	private HashSet<String> usagePattern = new HashSet<String>();

	public UsagePatternMethodVisitor() {
	}

	public UsagePatternMethodVisitor(HashMap<String, String> fieldMap) {
		this.fieldTypeMap = fieldMap;
	}

	public boolean visit(ExpressionStatement es) {
		String suspiciousType = extractUsagePattern(es.getExpression()).trim();
		if (suspiciousType.length() > 1) {
			usagePattern.add(suspiciousType);
		}
		return true;
	}

	public String getUsagePattern() {
		String bugs = "";
		for (String s : usagePattern) {
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

	private String extractUsagePattern(Expression exp) {
		if (exp instanceof MethodInvocation) {

			String invokeS = ((MethodInvocation) exp).toString();
			int indexOfParameter = invokeS.indexOf("(");
			if (indexOfParameter > 0) {
				int rightPara = invokeS.lastIndexOf(")");
				if (rightPara - indexOfParameter > 1) {
					// has parameter
					String[] parameters = invokeS.substring(indexOfParameter,
							rightPara).split(",");
					for (String para : parameters) {
						String[] paraType = para.split(" ");
						if (paraType.length > 1)
							variableTypeMap.put(paraType[1], paraType[0]);
					}
				}
				invokeS = invokeS.substring(0, indexOfParameter);
				// get parameter, add to variable list
			}
			int docIndex = invokeS.indexOf('.');
			if (docIndex > 0) {
				// has invoker
				Expression expression = ((MethodInvocation) exp)
						.getExpression();
				if (expression == null)
					return "";
				return ",2,"
						+ extractUsagePattern(((MethodInvocation) exp)
								.getExpression()) + "."
						+ ((MethodInvocation) exp).getName().toString();
			} else
				return ",2," + invokeS;
		} else if (exp instanceof FieldAccess) {
			FieldAccess fa = (FieldAccess) exp;
			return extractUsagePattern(fa.getExpression())
					+ fa.getName().toString();
		} else if (exp instanceof QualifiedName) {
			QualifiedName qa = (QualifiedName) exp;
			return extractUsagePattern(qa.getName()) + "."
					+ extractUsagePattern(qa.getQualifier());
		} else if (exp instanceof Assignment) {
			String leftside = extractUsagePattern(((Assignment) exp)
					.getLeftHandSide());
			// if (leftside.length() > 0 && leftside.charAt(0) > 'Z')
			return ",1," + leftside;
			// else
			// return leftside;
		} else {
			String expS = exp.toString();
			int dotIndex = expS.indexOf(".");
			if (dotIndex > 0) {
				return replaceToType(expS.substring(0, dotIndex)) + "."
						+ replaceToType(expS.substring(dotIndex + 1));
			} else {
				return replaceToType(expS);
			}
		}
	}

	private String replaceToType(String value) {
		// if (fieldTypeMap.containsKey(value))
		// return value;
		if (variableTypeMap.containsKey(value))
			return variableTypeMap.get(value);
		return value;
	}
	/*
	 * public boolean visit(AnonymousClassDeclaration node) { return true; }
	 * public boolean visit(ArrayAccess node) { return true; }
	 * 
	 * public boolean visit(ArrayInitializer node) { return true; } public
	 * boolean visit(Assignment node) { return true; } public boolean
	 * visit(CastExpression node) { return true; }
	 * 
	 * public boolean visit(ClassInstanceCreation node) { return true; } public
	 * boolean visit(FieldAccess node) { return true; } public boolean
	 * visit(ForStatement node) { return true; }public boolean visit(IfStatement
	 * node) { return true; } public boolean visit(MethodInvocation node) {
	 * return true; } public boolean visit(QualifiedName node) { return true; }
	 */

}
