package seal.haku.sytacticAnalyser.inconsistIdentifier.namingRules;

import java.util.HashSet;

import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;
import seal.haku.lexicalAnalyser.model.types.TokenType;

public abstract class ImpNamingRule {
	@SuppressWarnings("serial")
	protected HashSet<TokenType> verbTypes = new HashSet<TokenType>() {
		{
			// VB, VBD, VBG, VBN, VBP, VBZ
			add(TokenType.VB);
			add(TokenType.VBD);
			add(TokenType.VBG);
			add(TokenType.VBN);
			add(TokenType.VBP);
			add(TokenType.VBZ);
		}
	};
	@SuppressWarnings("serial")
	protected HashSet<TokenType> nounTypes = new HashSet<TokenType>() {
		{
			// NN, NNS, NNP, NNPS
			add(TokenType.NN);
			add(TokenType.NNS);
			add(TokenType.NNP);
			add(TokenType.NNPS);
		}
	};
	protected void informNamingBug(String methodName, String curseStatement,
			String recommendVerb) {
		System.out.println("Implementation inconsistency " + methodName
				+ "():  " + curseStatement);
	
		System.out.println("Recommend name: " + recommendVerb + "\n");
	}

	public abstract boolean meetCriteria(IdentifierNode mNode);
}
