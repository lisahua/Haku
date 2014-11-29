package seal.haku.lexicalAnalyser.model.nameNode;

import org.eclipse.jdt.core.dom.Type;

//import java.lang.reflect.Modifier;

public class ParameterIdentifierNode extends IdentifierNode {

	public ParameterIdentifierNode( Type type,String name,String filePath) {
		setType(type);
		setName(name);
		setFilePath(filePath);
	}



}
