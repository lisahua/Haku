package seal.haku.lexicalAnalyser.tokenizer;

import java.util.ArrayList;

import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;

public class CamelCaseSplitter  {
	private static CamelCaseSplitter splitter = new CamelCaseSplitter();

	public static CamelCaseSplitter getInstance() {
		return splitter;
	}

	private CamelCaseSplitter() {

	}

	public String[] getTokens(IdentifierNode node) {
		
		String name = node.getName();
		String[] tokens;
		// if all upper case, split with "_"
		if (allUpperCase(name)) {
			tokens = name.split("_");
		} else {
			tokens = name.split("(?=[A-Z][^A-Z])|_");
		}
		return tokens;
	}

	private boolean allUpperCase(String name) {
		char[] charArray = name.toCharArray();
		for (char c : charArray) {
			if (c >= 'a')
				return false;
		}
		return true;
	}

	public String[] executeSingleName(String node) {
		String[] tokenS = node.split("(?=[A-Z][^A-Z])|_|\\(|\\)|\\.");
		return tokenS;
	}
	public String[] splitStatement(String node) {
		String[] tokenS = node.split(" |_|\\(|\\)|\\.");
		return tokenS;
	}

	
}
