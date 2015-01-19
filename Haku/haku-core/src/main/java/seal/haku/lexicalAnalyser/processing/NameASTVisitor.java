package seal.haku.lexicalAnalyser.processing;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import seal.haku.lexicalAnalyser.model.IdentifierNode;
import seal.haku.lexicalAnalyser.model.IdentifierType;

class NameASTVisitor extends ASTVisitor {

	List<IdentifierNode> typeList = new ArrayList<IdentifierNode>();

	public boolean visit(TypeDeclaration decleration) {

		typeList.add(generateTypeNameNode(decleration));
		return true;
	}

	private IdentifierNode generateTypeNameNode(TypeDeclaration td) {
		// class declaration
		IdentifierNode node = new IdentifierNode(IdentifierType.CLASS);
		node.setModifier(td.getModifiers());
		node.setType(td.getSuperclassType());
		node.setName(td.getName().getIdentifier());

		// hierachy declaration
		ClassTypeVisitor fVisitor = new ClassTypeVisitor();
		td.accept(fVisitor);
		node.setChildren(fVisitor.getMethodList());
		node.setChildren2(fVisitor.getFieldList());
		return node;
	}

	public List<IdentifierNode> getTypeList() {
		return typeList;
	}

	public String printTypeNameNode() {
		StringBuilder sb = new StringBuilder();
		for (IdentifierNode tnode : typeList) {
			sb.append(tnode.getIdentifierType() + "\t" + tnode.getModifier()
					+ "\t" + tnode.getType() + "\t" + tnode.getName());
			for (IdentifierNode node : tnode.getChildren()) {
				sb.append("\t" + node.getIdentifierType() + "\t"
						+ node.getModifier() + "\t" + node.getType() + "\t"
						+ node.getName());
				for (IdentifierNode subNode : node.getChildren()) {
					sb.append("\t\t" + subNode.getIdentifierType() + "\t"
							+ subNode.getModifier() + "\t" + subNode.getType()
							+ "\t" + subNode.getName());
				}
			}
		}
		return sb.toString();
	}
}

class VariableVisitor extends ASTVisitor {
	ArrayList<IdentifierNode> nodeList = new ArrayList<IdentifierNode>();

	public boolean visit(VariableDeclarationStatement decleration) {
		nodeList.add(generateNameNode(decleration));
		return true;
	}

	public ArrayList<IdentifierNode> getClassInstances() {
		return nodeList;
	}

	public void printInstances() {
		for (IdentifierNode instance : nodeList)
			System.out.println(instance);
	}

	private IdentifierNode generateNameNode(VariableDeclarationStatement vd) {
		// variable declaration
		IdentifierNode node = new IdentifierNode(IdentifierType.VARIABLE);
		node.setModifier(vd.getModifiers());
		node.setType(vd.getType());

		@SuppressWarnings("rawtypes")
		List fragment = vd.fragments();
		String fString = "";
		for (Object o : fragment)
			fString += o.toString().split("=")[0] + "\t";
		node.setName(fString);

		return node;
	}
}

class ClassTypeVisitor extends ASTVisitor {
	ArrayList<IdentifierNode> methodList = new ArrayList<IdentifierNode>();
	ArrayList<IdentifierNode> fieldList = new ArrayList<IdentifierNode>();

	public boolean visit(MethodDeclaration decleration) {
		methodList.add(generateMethodNameNode(decleration));
		return true;
	}

	public boolean visit(FieldDeclaration decleration) {
		fieldList.add(generateFieldNameNode(decleration));
		return true;
	}

	public ArrayList<IdentifierNode> getFieldList() {
		return fieldList;
	}

	private IdentifierNode generateFieldNameNode(FieldDeclaration fd) {
		// field declaration
		IdentifierNode node = new IdentifierNode(IdentifierType.FIELD);
		node.setModifier(fd.getModifiers());
		node.setType(fd.getType());

		@SuppressWarnings("rawtypes")
		List fragment = fd.fragments();
		String fString = "";
		for (Object o : fragment)
			fString += o.toString().split("=")[0] + "\t";
		node.setName(fString);

		return node;
	}

	private IdentifierNode generateMethodNameNode(MethodDeclaration md) {
		// method declaration
		IdentifierNode node = new IdentifierNode(IdentifierType.METHOD);
		node.setModifier(md.getModifiers());
		node.setType(md.getReturnType2());
		node.setName(md.getName().getIdentifier());

		// field declaration
		VariableVisitor fVisitor = new VariableVisitor();
		md.accept(fVisitor);
		node.setChildren(fVisitor.getClassInstances());
		return node;
	}

	public ArrayList<IdentifierNode> getMethodList() {
		return methodList;
	}

}
