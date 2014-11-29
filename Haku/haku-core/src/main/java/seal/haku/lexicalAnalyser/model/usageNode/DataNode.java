package seal.haku.lexicalAnalyser.model.usageNode;

import java.util.HashSet;

import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;

public class DataNode extends UsageEdge {
	private HashSet<IdentifierNode> names = new HashSet<IdentifierNode>();

	private String type;

	public HashSet<IdentifierNode> getNames() {
		return names;
	}

	public void addNames(IdentifierNode name) {
		names.add(name);
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
