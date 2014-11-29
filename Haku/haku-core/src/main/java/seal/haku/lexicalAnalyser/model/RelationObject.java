package seal.haku.lexicalAnalyser.model;

import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;

public class RelationObject {
	private RelationType type;
	private IdentifierNode source;
	private IdentifierNode target;
	private String filePath;

	public RelationObject(RelationType typ, IdentifierNode s, IdentifierNode t,
			String path) {
		type = typ;
		source = s;
		target = t;
		filePath = path;
	}

	public RelationType getType() {
		return type;
	}

	public void setType(RelationType type) {
		this.type = type;
	}

	public IdentifierNode getSource() {
		return source;
	}

	public void setSource(IdentifierNode source) {
		this.source = source;
	}

	public IdentifierNode getTarget() {
		return target;
	}

	public void setTarget(IdentifierNode target) {
		this.target = target;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String toString() {
		return type + "," + source.getName() + ","
				+ target.getName() + "," + filePath;
	}

}
