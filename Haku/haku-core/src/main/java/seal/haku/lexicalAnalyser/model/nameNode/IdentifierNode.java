package seal.haku.lexicalAnalyser.model.nameNode;

//import java.lang.reflect.Modifier;
import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.Type;

import seal.haku.lexicalAnalyser.tokenizer.IdentifierGather;

public class IdentifierNode {
	private Type type;
	private NameToken[] nameToken = null;
	private int modifier;
	private String name;
	private String filePath = "";

	public String toString() {
		if (type == null)
			return name + "," + modifier + ",," + filePath;
		return name + "," + modifier + "," + type.toString() + "," + filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Type getType() {
		return type;
	}

	public NameToken[] getNameToken() {
		return nameToken;
	}

	public String getName() {
		return name;
	}

	public void setName(String identifier) {
		this.name = identifier;
		nameToken = IdentifierGather.executeNameAnalysis(identifier);
	}

	public IdentifierNode setType(Type type) {
		if (type != null)
			this.type = type;
		return this;
	}

	public NameToken[] getTokens() {
		return nameToken;
	}

	public void setTokens(NameToken[] list) {
		nameToken = list;
	}

	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		// String modifierS = Modifier.toString(modifier);
		if (Modifier.isPublic(modifier))
			this.modifier = Modifier.PUBLIC;
		else if (Modifier.isProtected(modifier))
			this.modifier = Modifier.PROTECTED;
		else if (Modifier.isPrivate(modifier))
			this.modifier = Modifier.PRIVATE;
	}

	// public boolean equals(Object obj) {
	// if (obj instanceof IdentifierNode) {
	// IdentifierNode that = (IdentifierNode) obj;
	// if (that.getName().equals(this.getName()))
	// return true;
	// }
	// return false;
	// }

	public String getTokenString() {
		String result = "";
		for (NameToken s : nameToken) {
			result += s.getToken() + "_" + s.getType() + " ";
		}
		return result;
	}

	public int hashCode() {
		return 0;
	}

}
