package seal.haku.sytacticAnalyser.inconsistIdentifier.namingRules;

import java.util.HashSet;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;

import seal.haku.lexicalAnalyser.model.IdentifierNode;
import seal.haku.lexicalAnalyser.model.NameToken;
import seal.haku.lexicalAnalyser.tokenizer.CamelCaseSplitter;
import seal.haku.lexicalAnalyser.tokenizer.POSTagger;

public class BooleanReturnRule extends ImpNamingRule {
	private static BooleanReturnRule rule = new BooleanReturnRule();
	private final String IS = "is";
	private HashSet<String> verbSet = new HashSet<String>() {
		{
			add(IS);
			add("contain");
			add("has");
		}
	};

	private BooleanReturnRule() {

	}

	public static BooleanReturnRule getInstance() {
		return rule;
	}

	@Override
	public boolean meetCriteria(IdentifierNode mNode) {
//		if (mNode.getNode() instanceof MethodDeclaration) {
//			MethodDeclaration md = (MethodDeclaration) mNode.getNode();
//	
//			if (md != null && md.getReturnType2() != null
//					&& md.getReturnType2().toString().equals("boolean")) {
//				// check method name
//				NameToken[] tokens = POSTagger.getInstance().executeSingleName(
//						CamelCaseSplitter.getInstance().executeSingleName(
//								mNode.getName()));
//				if (tokens[0]!=null   && !verbSet.contains(tokens[0].getToken())) {
//					// recommend name
//					String methodName = mNode.getName();
//					if (methodName.charAt(0) > 'Z') {
//						char newC = (char) (methodName.charAt(0) + 'A' - 'a');
//						methodName = newC + methodName.substring(1) + "d";
//					}
//					informNamingBug(mNode.getName(), "RETURN TYPE is BOOLEAN",
//							IS + methodName);
//				}
//			}
//		}
		return false;
	}

}
