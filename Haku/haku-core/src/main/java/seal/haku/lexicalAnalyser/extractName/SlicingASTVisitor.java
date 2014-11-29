package seal.haku.lexicalAnalyser.extractName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.*;

import seal.haku.lexicalAnalyser.model.DBEntity;
import seal.haku.lexicalAnalyser.model.nameNode.ClassIdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.FieldIdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.MethodIdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.NameNodeHashMap;
import seal.haku.lexicalAnalyser.model.nameNode.ParameterIdentifierNode;

public class SlicingASTVisitor extends PoorNameDetector {

	List<IdentifierNode> typeList = new ArrayList<IdentifierNode>();

	ArrayList<IdentifierNode> methodList = new ArrayList<IdentifierNode>();
	ArrayList<IdentifierNode> fieldList = new ArrayList<IdentifierNode>();
	HashMap<String, String> fieldValueTypes = new HashMap<String, String>();
	HashMap<String, String> variableTypes = new HashMap<String, String>();
	// key: name_<method>; value: field/variable_Type
	HashMap<String, ArrayList<String>> usageList = new HashMap<String, ArrayList<String>>();

	public SlicingASTVisitor() {
		
	}
	
	public SlicingASTVisitor(String filePath) {
		this.filePath = filePath;
	}

	public boolean visit(TypeDeclaration td) {
		// TODO hierarchy+invocation+dependency
		ClassIdentifierNode classNode = new ClassIdentifierNode(
				td.getModifiers(), td.getSuperclassType(), td.getName()
						.getIdentifier(), filePath);
		NameNodeHashMap.put(classNode);

		// ArrayList<IdentifierNode> mtdList = classVisitor.getMethodList();
		// for (IdentifierNode mtd : mtdList) {
		// NameNodeHashMap.addContainsRelation(classNode, mtd);
		// }
		// ArrayList<IdentifierNode> fieldList = classVisitor.getMethodList();
		// for (IdentifierNode field : fieldList) {
		// NameNodeHashMap.addContainsRelation(classNode, field);
		//
		// }
		return true;
	}

	public boolean visit(FieldDeclaration decleration) {
		IdentifierNode field = generateFieldNameNode(decleration);

		fieldList.add(field);

		return true;
	}

	public void endVisit(TypeDeclaration node) {
		for (Map.Entry<String, ArrayList<String>> entry : usageList.entrySet()) {
			for (String usage : entry.getValue()) {
				String[] usgToken = usage.split(" ");
				// if (usgToken.length < 2)
				// continue;
				String[] mtd = entry.getKey().split("~");
				if (mtd.length > 1) {
					if (fieldValueTypes.containsKey(mtd[1]))
						DBEntity.writeUsage(filePath, resolveValueType(mtd[1]),
								mtd[1], mtd[0], usage);
				} else {
					if (fieldValueTypes.containsKey(entry.getKey()))
						DBEntity.writeUsage(filePath,
								resolveValueType(entry.getKey()),
								entry.getKey(), node.getName().toString(),
								usage);
				}
			}
		}

	}

	public boolean visit(MethodDeclaration decleration) {
		// variableTypes.clear();

		methodList.add(generateMethodNameNode(decleration));
		return true;
	}

	// public boolean visit(Block node) {
	// // while (!nestLoop.isEmpty()) {
	// // if (nestLoop.peek().toString().contains(node.toString())) {
	// // nestLoop.push(node);
	// // return true;
	// // } else {
	// // parseBlock(nestLoop.pop());
	// // }
	// // }
	// // nestLoop.add(node);
	// return true;
	// }

	public ArrayList<IdentifierNode> getFieldList() {
		return fieldList;
	}

	public ArrayList<IdentifierNode> getMethodList() {
		return methodList;
	}

	private IdentifierNode generateFieldNameNode(FieldDeclaration fd) {
		// field declaration
		IdentifierNode node = new FieldIdentifierNode(fd.getModifiers(),
				fd.getType(), filePath);

		@SuppressWarnings("unchecked")
		List<VariableDeclarationFragment> fragment = fd.fragments();
		String fString = "";
		String name = "";

		// fieldValueTypes.put(field.getName().trim(),
		// field.getType().toString());
		for (VariableDeclarationFragment vdf : fragment) {
			String expS = parseExpression(vdf.getInitializer());
			if (expS != null)
				fString += expS + " ";
			name = vdf.getName().toString();
		}
		fieldValueTypes.put(name, fd.getType().toString());
		node.setName(name);
		addUsageList(name, fString);
		NameNodeHashMap.put(node);
		return node;
	}

	private IdentifierNode generateMethodNameNode(MethodDeclaration md) {
		// method declaration
		IdentifierNode mtdNode = new MethodIdentifierNode(md.getModifiers(),
				md.getReturnType2(), md.getName().getIdentifier(), filePath);
		NameNodeHashMap.put(mtdNode);
		// add parameter
		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> parameters = md.parameters();
		for (SingleVariableDeclaration para : parameters) {
			IdentifierNode paraNode = new ParameterIdentifierNode(
					para.getType(), para.getName().toString(), filePath);
			NameNodeHashMap.addContainsRelation(mtdNode, paraNode);
			variableTypes.put(paraNode.getName(), para.getType().toString());
		}

		String sb = parseBlock(md.getBody());
		aggregateSingleMethodUsage(mtdNode.getName() + "~" + sb);
		return mtdNode;
	}

	private String parseBlock(Block block) {
		if (block == null)
			return "";
		@SuppressWarnings("unchecked")
		List<Statement> statements = block.statements();

		String sb = "";
		for (Statement statement : statements) {
			String stmt = parseStatement(statement);
			if (stmt != null && !stmt.trim().equals(""))
				sb += stmt + ";";
		}
		return sb;
	}

	private void aggregateSingleMethodUsage(String sb) {

		String[] methodTkns = sb.split("~");
		if (methodTkns.length < 2)
			return;
		String mtd = methodTkns[0];

		String[] usages = methodTkns[1].split(";");
		HashMap<String, String> usageMap = new HashMap<String, String>();
		for (String usg : usages) {
			if (!usg.contains(":"))
				continue;
			String[] tokns = usg.split(":");
			tokns[0] = tokns[0].trim();
			if (usageMap.containsKey(tokns[0])) {
				String usgList = usageMap.get(tokns[0]);
				if (usgList.contains(tokns[1]))
					continue;
				else
					usageMap.put(tokns[0], usageMap.get(tokns[0]) + " "
							+ tokns[1]);
			} else
				usageMap.put(tokns[0], tokns[1]);
		}
		for (Map.Entry<String, String> entry : usageMap.entrySet())
			addUsageList(mtd + "~" + entry.getKey(), entry.getValue());
	}

	private String parseStatement(Statement stmt) {
		if (stmt instanceof Block) {
			return parseBlock((Block) stmt);
		} else if (stmt instanceof TryStatement) {
			TryStatement tryStatement = (TryStatement) stmt;
			String subUsage = parseBlock(tryStatement.getBody()) + " ";
			@SuppressWarnings("unchecked")
			List<CatchClause> catchClause = tryStatement.catchClauses();
			for (CatchClause cc : catchClause)
				subUsage += parseBlock(cc.getBody()) + " ";
			subUsage += parseBlock(tryStatement.getFinally()) + " ";
			return subUsage;
		} else if (stmt instanceof IfStatement) {
			String subUsage = parseExpression(((IfStatement) stmt)
					.getExpression()) + " ";
			subUsage += parseStatement(((IfStatement) stmt).getThenStatement())
					+ " ";
			subUsage += parseStatement(((IfStatement) stmt).getElseStatement())
					+ " ";
			return subUsage;
		} else if (stmt instanceof WhileStatement) {
			String subUsage = parseExpression(((WhileStatement) stmt)
					.getExpression());
			subUsage += parseStatement(((WhileStatement) stmt).getBody()) + " ";
			return subUsage;
		} else if (stmt instanceof ForStatement) {
			String subUsage = parseExpression(((ForStatement) stmt)
					.getExpression()) + " ";
			subUsage += parseStatement(((ForStatement) stmt).getBody()) + " ";
			return subUsage;
		} else if (stmt instanceof SwitchStatement) {
			return parseExpression(((SwitchStatement) stmt).getExpression());
		} else if (stmt instanceof SwitchCase) {
			return parseExpression(((SwitchCase) stmt).getExpression());
		} else if (stmt instanceof ExpressionStatement) {
			return parseExpression(((ExpressionStatement) stmt).getExpression());
		} else if (stmt instanceof VariableDeclarationStatement) {
			VariableDeclarationStatement vdep = (VariableDeclarationStatement) stmt;
			String resolvedType = resolveType(vdep.getType());
			@SuppressWarnings("unchecked")
			List<VariableDeclarationFragment> fragments = vdep.fragments();
			String usage = "";
			String name = "";
			for (VariableDeclarationFragment cdf : fragments) {
				name = cdf.getName().toString();
				String subUsage = parseExpression(cdf.getInitializer());

				if (subUsage != null)
					usage += subUsage + " ";
			}
			if (resolvedType != null) {
				variableTypes.put(name, resolvedType);
			}
			return usage;
		} else if (stmt instanceof ReturnStatement) {
			return parseExpression(((ReturnStatement) stmt).getExpression());
		}
		return "";

	}

	private String parseExpression(Expression exp) {
		if (exp == null)
			return "";
		if (exp instanceof Name) {
			resolveValueType(exp.toString());
		}
		if (exp instanceof Assignment) {
			return parseExpression(((Assignment) exp).getLeftHandSide());

		} else if (exp instanceof FieldAccess) {
			String name = ((FieldAccess) exp).getName().toString();
			if (fieldValueTypes.containsKey(name))
				return name+" ";
		} else if (exp instanceof VariableDeclarationExpression) {
			VariableDeclarationExpression vdep = (VariableDeclarationExpression) exp;
			String resolvedType = resolveType(vdep.getType());
			if (resolvedType != null)
				variableTypes.put(vdep.fragments().get(0).toString(),
						resolvedType);
			@SuppressWarnings("unchecked")
			List<VariableDeclarationFragment> fragments = vdep.fragments();
			for (VariableDeclarationFragment cdf : fragments) {
				parseExpression(cdf.getInitializer());
			}
		} else if (exp instanceof MethodInvocation) {
			Expression mExp = ((MethodInvocation) exp).getExpression();
			// call other methods;
			if (mExp == null)
				return ((MethodInvocation) exp).getName().toString();
			String type = resolveValueType(mExp.toString());

			return mExp.toString() + ":" + type + "."
					+ ((MethodInvocation) exp).getName().toString();
		} else if (exp instanceof InstanceofExpression) {

		} else if (exp instanceof CastExpression) {

		} else if (exp instanceof PrefixExpression) {
			parseExpression(((PrefixExpression) exp).getOperand());
		}
		// cannot handle anonymous class
		else if (exp instanceof ClassInstanceCreation) {
			ClassInstanceCreation cic = (ClassInstanceCreation) exp;
			String usage = parseExpression(cic.getExpression()) + " ";
			if (cic.getAnonymousClassDeclaration() == null)
				return "";
			@SuppressWarnings("unchecked")
			List<BodyDeclaration> bodyDec = cic.getAnonymousClassDeclaration()
					.bodyDeclarations();
			for (BodyDeclaration body : bodyDec) {
				usage += parseInnerClass(body) + " ";
			}
			return usage;
		}
		return "";
	}

	private String parseInnerClass(BodyDeclaration body) {
		body.accept(this);
		return "";
	}

	private String resolveType(Type type) {

		if (type instanceof PrimitiveType)
			return null;
		else if (type instanceof ArrayType)
			return resolveType(((ArrayType) type).getComponentType());
		else if (type instanceof ParameterizedType)
			return resolveType(((ParameterizedType) type).getType());
		else
			return type.toString();
	}

	private String resolveValueType(String value) {
		if (fieldValueTypes.containsKey(value))
			return fieldValueTypes.get(value);
		if (variableTypes.containsKey(value))
			return variableTypes.get(value);
		return value;
	}

	private void addUsageList(String name, String usage) {
		name = name.trim();
		usage = usage.trim();
		if (usage == null || usage.trim().equals(""))
			return;
		if (usageList.containsKey(name)) {
			usageList.get(name).add(usage);
		} else {
			usageList.put(name, new ArrayList<String>());
			usageList.get(name).add(usage);
		}
	}
}
