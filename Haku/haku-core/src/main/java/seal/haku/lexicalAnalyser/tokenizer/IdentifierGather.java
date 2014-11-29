package seal.haku.lexicalAnalyser.tokenizer;

import seal.haku.lexicalAnalyser.model.nameNode.NameToken;

public class IdentifierGather {
	private static IdentifierGather gather = new IdentifierGather();
	private static CamelCaseSplitter splitter = CamelCaseSplitter.getInstance();
	private static POSTagger tagger = POSTagger.getInstance();

	private IdentifierGather() {

	}

	public static IdentifierGather getInstance() {
		return gather;
	}

	public static NameToken[] executeNameAnalysis(String name) {
		NameToken[] tokens = null;
		String[] stringTokens = splitter.executeSingleName(name);
		stringTokens = AbbreviationExpander.executeSingleName(stringTokens);
		tokens = tagger.executeSingleName(stringTokens);
		return tokens;
	}
	
}
