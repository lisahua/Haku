package seal.haku.sytacticAnalyser.inconsistIdentifier.namingRules;

import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.NameToken;
import seal.haku.lexicalAnalyser.tokenizer.CamelCaseSplitter;
import seal.haku.lexicalAnalyser.tokenizer.POSTagger;
import seal.haku.sytacticAnalyser.StatementVisitor;

public class ISNamingRule extends ImpNamingRule {
	private final String IS = "is";
	private final String GET = "get";
	private static ISNamingRule rule = new ISNamingRule();

	private ISNamingRule() {

	}

	public static ISNamingRule getInstance() {
		return rule;
	}

	@Override
	public boolean meetCriteria(IdentifierNode mNode) {
		NameToken verbToken = mNode.getTokens()[0];
		if (verbToken.getToken().contains(IS)) {
			return identifyImpVerb(mNode);
		}
		return false;
	}

//	private boolean identifyImpVerb(IdentifierNode mNode) {
//		StatementVisitor sVisitor = new StatementVisitor();
//		mNode.getNode().accept(sVisitor);
//		String returnS = sVisitor.getReturnStatement().toString();
//		if (returnS.contains("true[ +];") || returnS.contains("false[ +];"))
//			return false;
//		else {
//			return generateName(mNode.getName(), returnS);
//		}
//	}

	private boolean identifyImpVerb(IdentifierNode mNode) {
		// TODO Auto-generated method stub
		return false;
	}

//	private boolean generateName(String name, String returnS) {
//		// change verb
//		String methodName = name.replace(IS, GET);
//		// add noun
//		NameToken[] tokens = POSTagger.getInstance().executeSingleName(
//				CamelCaseSplitter.getInstance().executeSingleName(methodName));
//		if (tokens.length > 1) {
//			for (int i = 1; i < tokens.length; i++) {
//				if (nounTypes.contains(tokens[i].getType())) {
//					return false;
//				}
//			}
//		}
//		// dont have noun, extract noun
//		NameToken[] returnTokens = POSTagger.getInstance().executeSingleName(
//				CamelCaseSplitter.getInstance().splitStatement(returnS.replace("return", "")));
//		String firstNoun = "";
//		for (NameToken token : returnTokens) {
//			if (token!=null && nounTypes.contains(token.getType())) {
//				firstNoun = token.getToken();
//				break;
//			}
//		}
//		informNamingBug(name, returnS, methodName + firstNoun);
//		return true;
//	}
}
