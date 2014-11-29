package seal.haku.lexicalAnalyser.model.nameNode;

import org.eclipse.jdt.core.dom.Type;

//import java.lang.reflect.Modifier;

public class MethodIdentifierNode extends IdentifierNode {

	public MethodIdentifierNode(int modifiers, Type returnType,
			String identifier,String filePath) {
		setModifier(modifiers);
		setType(returnType);
		setName(identifier);
		setFilePath(filePath);
	}

}
