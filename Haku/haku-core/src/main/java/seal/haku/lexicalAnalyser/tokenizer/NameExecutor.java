package seal.haku.lexicalAnalyser.tokenizer;

import java.util.ArrayList;

import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;

public abstract class NameExecutor {

	public ArrayList<IdentifierNode> executeNames(
			ArrayList<IdentifierNode> typeNodes) {
		
		return typeNodes;
	}

	public abstract String[] executeSingleName(String[] tokens);
	
	public abstract void finishExecution() ;
	public abstract void beforeExecution();
}
