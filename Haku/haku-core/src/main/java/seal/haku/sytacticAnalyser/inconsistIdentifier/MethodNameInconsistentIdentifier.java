package seal.haku.sytacticAnalyser.inconsistIdentifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import seal.haku.lexicalAnalyser.model.IdentifierNode;
import seal.haku.lexicalAnalyser.model.TokenPairModel;

public class MethodNameInconsistentIdentifier implements
		NamingRecommenderInterface {
	HashSet<TokenPairModel> significantPairs = new HashSet<TokenPairModel>();
	HashSet<String> existingToken = new HashSet<String>();

	private void identifyInconsistent(HashMap<TokenPairModel, Integer> namePairs) {
		for (Map.Entry<TokenPairModel, Integer> item : namePairs.entrySet()) {
			item.getKey().setNumber(item.getValue());
			if (item.getValue() >= 2) {
				significantPairs.add(item.getKey());
				existingToken.add(item.getKey().getTextA().toLowerCase());
				existingToken.add(item.getKey().getTextB().toLowerCase());
			}
		}
		for (Map.Entry<TokenPairModel, Integer> item : namePairs.entrySet()) {
			if (!significantPairs.contains(item.getKey())) {
				if (existingToken.contains(item.getKey().getTextA()
						.toLowerCase())) {
					informNamingBug(item.getKey(), item.getKey().getTextA(),
							item.getKey().getTextB());
				}
				if (existingToken.contains(item.getKey().getTextB()
						.toLowerCase())) {
					informNamingBug(item.getKey(), item.getKey().getTextB(),
							item.getKey().getTextA());
				}
			}
		}
	}

	private String informNamingBug(TokenPairModel pair, String sameA,
			String conflictItem) {
		String idealName = "";
		for (TokenPairModel item : significantPairs) {
			if (item.getTextA().toLowerCase().equals(sameA.toLowerCase())) {
				String target = item.getTextB();
				if (conflictItem.charAt(0) < 'a') {
					char first = (char) (target.charAt(0) + 'A' - 'a');
					target = first + target.substring(1);
				}
				idealName = pair.getNameB().replace(conflictItem, target);
				break;
			}
			if (item.getTextB().toLowerCase().equals(sameA.toLowerCase())) {
				String target = item.getTextA();
				if (conflictItem.charAt(0) > 'a') {
					char first = (char) (target.charAt(0) + 'A' - 'a');
					target = first + target.substring(1);
				}

				idealName = pair.getNameB().replace(conflictItem, target);
				break;
			}
		}
		System.out.println("Method inconsistency: " + pair.getNameA()
				+ ":" + pair.getNameB() + "\n" + " Recommend name: "
				+ idealName);
		return idealName;
	}


	public void identifyAbnormal(IdentifierNode cNode) {
		SimpleNamingPairGenerator pairGenerator = new SimpleNamingPairGenerator();
		HashMap<TokenPairModel, Integer> namePairs = pairGenerator
				.extractNamingGroup(cNode);
		identifyInconsistent(namePairs);
	}

}
