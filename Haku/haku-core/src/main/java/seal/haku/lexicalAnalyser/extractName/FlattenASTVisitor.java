package seal.haku.lexicalAnalyser.extractName;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import seal.haku.lexicalAnalyser.model.nameNode.ClassIdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.FieldIdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.MethodIdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.NameNodeHashMap;
import seal.haku.lexicalAnalyser.model.nameNode.ParameterIdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.VariableIdentifierNode;

class FlattenASTVisitor extends ASTVisitor {

	List<IdentifierNode> typeList = new ArrayList<IdentifierNode>();
	private String filePath;

	public FlattenASTVisitor(String filePath) {
		this.filePath = filePath;
	}

	public boolean visit(TypeDeclaration td) {
		// TODO hierarchy+invocation+dependency
		ClassIdentifierNode classNode = new ClassIdentifierNode(
				td.getModifiers(), td.getSuperclassType(), td.getName()
						.getIdentifier(), filePath);
		NameNodeHashMap.put(classNode);
		FlattenClassTypeVisitor classVisitor = new FlattenClassTypeVisitor(
				filePath, td);
		td.accept(classVisitor);

		ArrayList<IdentifierNode> mtdList = classVisitor.getMethodList();
		for (IdentifierNode mtd : mtdList) {
			NameNodeHashMap.addContainsRelation(classNode, mtd);
		}
		ArrayList<IdentifierNode> fieldList = classVisitor.getMethodList();
		for (IdentifierNode field : fieldList) {
			NameNodeHashMap.addContainsRelation(classNode, field);

		}
		return true;
	}

	public String printTypeNameNode() {
		StringBuilder sb = new StringBuilder();
		for (IdentifierNode tnode : typeList) {
			sb.append("\t" + tnode.getModifier() + "\t" + tnode.getType()
					+ "\t" + tnode.getName());
			// for (IdentifierNode node : tnode.getChildren()) {
			// sb.append("\t" + "\t" + node.getModifier() + "\t"
			// + node.getType() + "\t" + node.getName());
			// for (IdentifierNode subNode : node.getChildren()) {
			// sb.append("\t\t" + subNode.getModifier() + "\t"
			// + subNode.getType() + "\t" + subNode.getName());
			// }
			// }
		}
		return sb.toString();
	}

}

class FlattenClassTypeVisitor extends ASTVisitor {
	ArrayList<IdentifierNode> methodList = new ArrayList<IdentifierNode>();
	ArrayList<IdentifierNode> fieldList = new ArrayList<IdentifierNode>();
	private String filePath;
	private TypeDeclaration clazz;

	public FlattenClassTypeVisitor(String filePath, TypeDeclaration td) {
		this.filePath = filePath;
		clazz = td;
	}

	public boolean visit(FieldDeclaration decleration) {
		fieldList.add(generateFieldNameNode(decleration));
		return true;
	}

	public boolean visit(MethodDeclaration decleration) {
		methodList.add(generateMethodNameNode(decleration));
		return true;
	}

	public ArrayList<IdentifierNode> getFieldList() {
		return fieldList;
	}

	private IdentifierNode generateFieldNameNode(FieldDeclaration fd) {
		// field declaration
		IdentifierNode node = new FieldIdentifierNode(fd.getModifiers(),
				fd.getType(), filePath);
		@SuppressWarnings("rawtypes")
		List fragment = fd.fragments();
		String fString = "";
		for (Object o : fragment)
			fString += o.toString().split("=")[0] + "\t";
		node.setName(fString);
		NameNodeHashMap.put(node);
		if (fd.toString().contains("="))
			NameNodeHashMap.parseAndAddStatement(fd.toString(), clazz.getName()
					.toString());
		return node;
	}

	private IdentifierNode generateMethodNameNode(MethodDeclaration md) {
		// method declaration
		// System.out.println(md.getBody());
		IdentifierNode mtdNode = new MethodIdentifierNode(md.getModifiers(),
				md.getReturnType2(), md.getName().getIdentifier(), filePath);
		NameNodeHashMap.put(mtdNode);

		FlattenVariableVisitor vVisitor = new FlattenVariableVisitor(filePath,
				clazz);
		md.accept(vVisitor);
		ArrayList<IdentifierNode> variableList = vVisitor.getVariableNodes();
		// add variables
		for (IdentifierNode variable : variableList) {
			NameNodeHashMap.addContainsRelation(mtdNode, variable);
		}
		// add parameter
		@SuppressWarnings("unchecked")
		List<SingleVariableDeclaration> parameters = md.parameters();
		for (SingleVariableDeclaration para : parameters) {
			IdentifierNode paraNode = new ParameterIdentifierNode(
					para.getType(), para.getName().toString(), filePath);
			NameNodeHashMap.addContainsRelation(mtdNode, paraNode);
		}
		return mtdNode;
	}

	public ArrayList<IdentifierNode> getMethodList() {
		return methodList;
	}

}

class FlattenVariableVisitor extends ASTVisitor {
	ArrayList<IdentifierNode> variableList = new ArrayList<IdentifierNode>();
	private String filePath;
	private TypeDeclaration clazz;

	public FlattenVariableVisitor(String filePath, TypeDeclaration td) {
		this.filePath = filePath;
		clazz = td;
	}

	public boolean visit(VariableDeclarationStatement decleration) {
		variableList.add(generateNameNode(decleration));
		return true;
	}

	public boolean visit(ExpressionStatement node) {
		// System.out.println("visit expression " + node.toString());
		NameNodeHashMap.parseAndAddStatement(node.toString(), clazz.getName()
				.toString());
		return true;
	}

	public ArrayList<IdentifierNode> getVariables() {
		return variableList;
	}

	public void printInstances() {
		for (IdentifierNode instance : variableList)
			System.out.println(instance);
	}

	private IdentifierNode generateNameNode(VariableDeclarationStatement vd) {
		// variable declaration
		// System.out.println("visit variable declare " + vd.toString());
		IdentifierNode node = new VariableIdentifierNode(vd.getType(), filePath);

		@SuppressWarnings("rawtypes")
		List fragment = vd.fragments();
		String[] fTkns = null;

		for (Object o : fragment) {
			fTkns = o.toString().split("=");
		}

		node.setName(fTkns[0]);
		NameNodeHashMap.put(node);
		// only has declaration, no initialization;otherwise, assignment
		NameNodeHashMap.parseAndAddStatement(vd.toString(), clazz.getName()
				.toString());
		// if (fTkns.length > 1) {
		// String init = fTkns[1];
		// init = generalizeMtdInv(init);
		// NameNodeHashMap.addAssignmentRelation(init, node);
		// }
		return node;
	}

	public ArrayList<IdentifierNode> getVariableNodes() {
		return variableList;
	}

}
