package seal.haku.sytacticAnalyser.inconsistIdentifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import seal.haku.lexicalAnalyser.model.IdentifierNode;
import seal.haku.lexicalAnalyser.model.NameToken;
import seal.haku.lexicalAnalyser.model.TokenPairModel;

public class VariableInconsistentIdentifier implements
		NamingRecommenderInterface {
	private final String WILDCARD = "$m$";
	private final int SAME_VERB_ThRESHOLD = 1;
	private final int DIFF_VERB_ThRESHOLD = 3;
	private final int NAME_BUG_THRESHOLD = 2;


	public void identifyAbnormal(IdentifierNode cNode) {
		extractNamingGroupInSingleClass(cNode);
	}

	// compare edit distance
	private void extractNamingGroupInSingleClass(IdentifierNode cNode) {
		HashMap<String, ArrayList<IdentifierNode>> verbMap = new HashMap<String, ArrayList<IdentifierNode>>();
		// for all verbs
		for (IdentifierNode node : cNode.getChildren()) {
			String firstToken = node.getTokens().get(0).getToken();
			if (verbMap.containsKey(firstToken)) {
				verbMap.get(firstToken).add(node);
			} else {
				ArrayList<IdentifierNode> nodeList = new ArrayList<IdentifierNode>();
				nodeList.add(node);
				verbMap.put(firstToken, nodeList);
			}
		}
		// for each verb,identify wildcard map
		for (Map.Entry<String, ArrayList<IdentifierNode>> entry : verbMap
				.entrySet()) {
			if (entry.getValue().size() < DIFF_VERB_ThRESHOLD)
				continue;
			HashMap<String, ArrayList<TokenPairModel>> wildcardMap = identifyVariables(entry
					.getValue());
			// for each wildcard map, inform naming bug
			informNamingBug(wildcardMap);
		}
	}

	private HashMap<String, ArrayList<TokenPairModel>> identifyVariables(
			ArrayList<IdentifierNode> nodes) {
		ArrayList<String> methodFeature = extractMethodFeature(nodes);
		HashMap<String, ArrayList<TokenPairModel>> wildcardMap = new HashMap<String, ArrayList<TokenPairModel>>();

		for (int i = 0; i < nodes.size(); i++) {
			for (IdentifierNode vNode : nodes.get(i).getChildren()) {
				String vName = vNode.getName();
				if (vName.toLowerCase().contains(
						methodFeature.get(i).toLowerCase())) {
					String mFeature = methodFeature.get(i);
					String name = vNode.getName().replace(mFeature, WILDCARD);
					if (!name.contains(WILDCARD)) {
						if (mFeature.charAt(0) < 'a') {
							char newC = (char) (mFeature.charAt(0) + 'a' - 'A');
							mFeature = newC + mFeature.substring(1);
							name = vNode.getName().replace(mFeature, WILDCARD);
						}
					}
					if (wildcardMap.containsKey(name)) {
						wildcardMap.get(name)
								.add(new TokenPairModel(vName, methodFeature
										.get(i)));
					} else {
						ArrayList<TokenPairModel> nameList = new ArrayList<TokenPairModel>();
						nameList.add(new TokenPairModel(vName, methodFeature
								.get(i)));
						wildcardMap.put(name, nameList);
					}

				}
			}
		}
		return wildcardMap;
	}

	private ArrayList<String> extractMethodFeature(
			ArrayList<IdentifierNode> nodes) {
		int start = 0, end = 0;
		int prev;
		while (true) {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			prev = start;
			for (int j = 0; j < nodes.size(); j++) {
				String token = nodes.get(j).getTokens().get(start).getToken();
				int count = 0;
				if (map.containsKey(token))
					count = map.get(token);
				map.put(token, count + 1);
			}
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() == nodes.size()) {
					start++;
					break;
				}
			}
			if (prev == start)
				break;
		}
		while (true) {
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			prev = end;
			for (int j = 0; j < nodes.size(); j++) {
				ArrayList<NameToken> tokens = nodes.get(j).getTokens();
				String token = tokens.get(tokens.size() - end - 1).getToken();
				int count = 0;
				if (map.containsKey(token))
					count = map.get(token);
				map.put(token, count + 1);
			}
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() >= nodes.size() - SAME_VERB_ThRESHOLD) {
					end++;
					break;
				}
			}
			if (prev == end)
				break;
		}

		ArrayList<String> features = new ArrayList<String>();
		for (IdentifierNode node : nodes) {
			String nodeS = "";
			ArrayList<NameToken> nTokens = node.getTokens();
			for (int i = start; i < nTokens.size() - end; i++)
				nodeS += nTokens.get(i).getToken();
			features.add(nodeS);
		}
		return features;
	}

	private void informNamingBug(
			HashMap<String, ArrayList<TokenPairModel>> wildcardMap) {
		HashSet<String> impStringSet = new HashSet<String>();
		HashSet<TokenPairModel> suspectNodes = new HashSet<TokenPairModel>();
		for (Map.Entry<String, ArrayList<TokenPairModel>> entry : wildcardMap
				.entrySet()) {
			if (entry.getValue().size() >= NAME_BUG_THRESHOLD) {
				impStringSet.add(entry.getKey());
			} else {
				suspectNodes.add(entry.getValue().get(0));
			}
		}

		for (TokenPairModel node : suspectNodes) {
			for (String impString : impStringSet) {
				impString = impString.replace(WILDCARD, node.getTextB());
				if (impString.charAt(0) < 'a') {
					char newC = (char) (impString.charAt(0) + 'a' - 'A');
					impString = newC + impString.substring(1);
				}
				System.out.println("Variable inconsistency: "
						+ node.getTextA());
				System.out.println("Recomment variable: " + impString);
			}
		}
	}
}
