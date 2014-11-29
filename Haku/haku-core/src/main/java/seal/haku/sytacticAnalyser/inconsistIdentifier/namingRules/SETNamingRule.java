package seal.haku.sytacticAnalyser.inconsistIdentifier.namingRules;

import java.util.HashSet;

import seal.haku.lexicalAnalyser.model.RelationAnalyzer;
import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.NameToken;
import seal.haku.sytacticAnalyser.StatementVisitor;

public class SETNamingRule extends ImpNamingRule {
	private final String SET = "set";
	private static SETNamingRule rule = new SETNamingRule();

	private SETNamingRule() {

	}

	public static SETNamingRule getInstance() {
		return rule;

	}

	@Override
	public boolean meetCriteria(IdentifierNode mNode) {
		NameToken verbToken = mNode.getTokens()[0];
		if (!verbTypes.contains(verbToken.getType())
				&& !verbToken.getToken().contains(SET)) {
			return identifyImpVerb(mNode);
		}
		return false;
	}

	private boolean identifyImpVerb(IdentifierNode mNode) {
		// TODO Auto-generated method stub
		return false;
	}

//	private boolean identifyImpVerb(IdentifierNode mNode) {
//		StatementVisitor sVisitor = new StatementVisitor();
//		mNode.getNode().accept(sVisitor);
//		HashSet<IdentifierNameModel> expressions = sVisitor
//				.getExpressionStatement();
//		for (IdentifierNameModel name : expressions) {
//			if (name.getName().contains(SET)) {
//				String methodName = mNode.getName();
//				if (methodName.charAt(0) > 'Z') {
//					char newC = (char) (methodName.charAt(0) + 'A' - 'a');
//					methodName = newC + methodName.substring(1);
//				}
//				
//				informNamingBug(methodName, name.getName(), SET+methodName);
//				return true;
//			}
//		}
//		return false;
//	}
}
