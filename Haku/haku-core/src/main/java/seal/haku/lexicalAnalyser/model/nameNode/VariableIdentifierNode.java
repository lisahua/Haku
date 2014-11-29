package seal.haku.lexicalAnalyser.model.nameNode;

import org.eclipse.jdt.core.dom.Type;

//import java.lang.reflect.Modifier;

public class VariableIdentifierNode extends IdentifierNode {
	private String methodInitialize = "";

	public VariableIdentifierNode(Type type, String filePath) {
		setType(type);
		setFilePath(filePath);
	}
	public String getMethodInitialize() {
		return methodInitialize;
	}

	public void setMethodInitialize(String methodInitialize) {
		this.methodInitialize = methodInitialize;
	}


}
