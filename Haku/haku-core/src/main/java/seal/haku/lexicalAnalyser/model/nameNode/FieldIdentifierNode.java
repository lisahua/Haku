package seal.haku.lexicalAnalyser.model.nameNode;

import org.eclipse.jdt.core.dom.Type;

//import java.lang.reflect.Modifier;

public class FieldIdentifierNode extends IdentifierNode {

	public FieldIdentifierNode(int modifiers, Type type,String filePath) {
		setModifier(modifiers);
		setType(type);
		setFilePath(filePath);
	}



}
