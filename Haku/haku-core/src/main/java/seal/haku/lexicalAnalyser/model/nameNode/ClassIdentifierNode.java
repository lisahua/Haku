package seal.haku.lexicalAnalyser.model.nameNode;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.Type;

//import java.lang.reflect.Modifier;

public class ClassIdentifierNode extends IdentifierNode {
	private ArrayList<FieldIdentifierNode> fields = new ArrayList<FieldIdentifierNode>();
	private ArrayList<MethodIdentifierNode> methods = new ArrayList<MethodIdentifierNode>();

	public ClassIdentifierNode(int modifiers, Type superclassType,
			String identifier,String filePath) {
		setModifier(modifiers);
		setType(superclassType);
		setName(identifier);
		setFilePath(filePath);
	}
	
	public ArrayList<FieldIdentifierNode> getFields() {
		return fields;
	}

	public void setFields(ArrayList<FieldIdentifierNode> fields) {
		this.fields = fields;
	}

	public void addFields(FieldIdentifierNode field) {
		fields.add(field);
	}

	public void addMethod(MethodIdentifierNode method) {
		methods.add(method);
	}

	public ArrayList<MethodIdentifierNode> getMethods() {
		return methods;
	}

	public void setMethods(ArrayList<MethodIdentifierNode> methods) {
		this.methods = methods;
	}

}
