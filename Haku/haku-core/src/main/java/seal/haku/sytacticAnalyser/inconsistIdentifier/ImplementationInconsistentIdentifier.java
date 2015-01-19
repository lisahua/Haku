package seal.haku.sytacticAnalyser.inconsistIdentifier;

import java.util.HashSet;

import seal.haku.lexicalAnalyser.model.IdentifierNode;
import seal.haku.sytacticAnalyser.inconsistIdentifier.namingRules.BooleanReturnRule;
import seal.haku.sytacticAnalyser.inconsistIdentifier.namingRules.ISNamingRule;
import seal.haku.sytacticAnalyser.inconsistIdentifier.namingRules.ImpNamingRule;
import seal.haku.sytacticAnalyser.inconsistIdentifier.namingRules.SETNamingRule;

public class ImplementationInconsistentIdentifier implements
		NamingRecommenderInterface {
	@SuppressWarnings("serial")
	private HashSet<ImpNamingRule> suspectVerbSet = new HashSet<ImpNamingRule>() {
		{
			add(SETNamingRule.getInstance());
			add(ISNamingRule.getInstance());
			add(BooleanReturnRule.getInstance());
		}
	};

	
	public void identifyAbnormal(IdentifierNode cNode) {
		for (IdentifierNode mNode : cNode.getChildren()) {
			identifyInconsistentMethod(mNode);
		}
	}

	private void identifyInconsistentMethod(IdentifierNode mNode) {
		for (ImpNamingRule rule : suspectVerbSet) {
			rule.meetCriteria(mNode);
		}
	}

}
