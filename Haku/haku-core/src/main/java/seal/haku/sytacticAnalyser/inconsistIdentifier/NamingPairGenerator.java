package seal.haku.sytacticAnalyser.inconsistIdentifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import seal.haku.lexicalAnalyser.model.TokenPairModel;
import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.NameToken;

public class NamingPairGenerator {
	private HashMap<TokenPairModel, Integer> namePairModel = new HashMap<TokenPairModel, Integer>();
	private static double SIMILAR_THRESHOLD = 0.5;

	// @SuppressWarnings("serial")
	// HashSet<TokenType> nounSet = new HashSet<TokenType>() {
	// {
	// nounSet.add(TokenType.NN);
	// nounSet.add(TokenType.NNS);
	// nounSet.add(TokenType.NNP);
	// nounSet.add(TokenType.NNPS);
	// }
	// };

	public HashMap<TokenPairModel, Integer> extractNamingGroup(
			IdentifierNode cNode) {
		return extractNamingGroupInSingleClass(cNode);
	}

	// compare edit distance
	private HashMap<TokenPairModel, Integer> extractNamingGroupInSingleClass(
			IdentifierNode cNode) {

//		ArrayList<IdentifierNode> fNodes = cNode.getChildren2();
//		int lenF = fNodes.size();
//		for (int i = 0; i < lenF; i++) {
//			for (int j = i + 1; j < lenF; j++) {
//				calFieldDistance(fNodes.get(i).getTokens(), fNodes.get(j)
//						.getTokens(), fNodes.get(i).getName(), fNodes.get(j)
//						.getName());
//			}
//		}
//		ArrayList<IdentifierNode> mNodes = cNode.getChildren();
//		int lenM = mNodes.size();
//		for (int i = 0; i < lenM; i++) {
//			for (int j = i + 1; j < lenM; j++) {
//				calEditDistance(mNodes.get(i).getTokens(), mNodes.get(j)
//						.getTokens(), mNodes.get(i).getName(), mNodes.get(j)
//						.getName());
//			}
//		}
		return namePairModel;
	}

	private double calEditDistance(ArrayList<NameToken> tokenA,
			ArrayList<NameToken> tokenB, String nameA, String nameB) {
		int lenA = tokenA.size();
		int lenB = tokenB.size();
		if (lenA == 1 || lenB == 1)
			return 0;
		if (!tokenA.get(0).getToken().equals(tokenB.get(0).getToken())
				&& (!tokenA.get(lenA - 1).getToken()
						.equals(tokenB.get(lenB - 1).getToken())))
			return 0;
		if (lenA <= 3 || lenB <= 3)
			return calSimilarityLessThanThree(tokenA, tokenB, nameA, nameB);
		int[][] matrix = new int[lenA + 1][lenB + 1];
		TreeSet<Integer> sameTokenA = new TreeSet<Integer>();
		TreeSet<Integer> sameTokenB = new TreeSet<Integer>();

		for (int i = 0; i < lenA; i++)
			matrix[i][0] = i;
		for (int j = 1; j < lenB; j++)
			matrix[0][j] = j;

		for (int j = 1; j < lenB; j++) {
			for (int i = 1; i < lenA; i++) {
				if (tokenA.get(i).getToken().equals(tokenB.get(j).getToken())) {
					matrix[i][j] = matrix[i - 1][j - 1];
					sameTokenA.add(i);
					sameTokenB.add(j);
				} else {
					matrix[i][j] = Math.min(matrix[i - 1][j] + 1,
							Math.min(matrix[i][j - 1], matrix[i - 1][j - 1]));
				}
			}
		}
		double rate = 1 - (matrix[lenA][lenB]) * 1.0 / Math.min(lenA, lenB);
		if (rate >= SIMILAR_THRESHOLD)
			addToNamingPairDictionary(tokenA, tokenB, sameTokenA, sameTokenB,
					nameA, nameB);

		return rate;
	}

	@SuppressWarnings("unchecked")
	private double calSimilarityLessThanThree(ArrayList<NameToken> tokenA,
			ArrayList<NameToken> tokenB, String nameA, String nameB) {
		int similarity = 0;

		ArrayList<NameToken> tokenACopy = (ArrayList<NameToken>) tokenA.clone();
		ArrayList<NameToken> tokenBCopy = (ArrayList<NameToken>) tokenB.clone();
		int aPointer = 0;
		int bPointer = 0;
		while (aPointer < tokenACopy.size()
				&& bPointer < tokenBCopy.size()
				&& tokenACopy.get(aPointer).getToken()
						.equals(tokenBCopy.get(bPointer).getToken())) {
			if (tokenACopy.get(0).equals(tokenBCopy.get(0))) {
				similarity++;
				tokenACopy.remove(aPointer);
				tokenBCopy.remove(aPointer);
			}
			aPointer++;
			bPointer++;
		}
		aPointer = tokenACopy.size();
		aPointer = tokenBCopy.size();
		while (aPointer > 0
				&& bPointer > 0
				&& tokenACopy.get(aPointer).getToken()
						.equals(tokenBCopy.get(bPointer).getToken())) {
			if (tokenACopy.get(aPointer).getToken()
					.equals(tokenBCopy.get(bPointer).getToken())) {
				similarity++;
				tokenACopy.remove(aPointer);
				tokenBCopy.remove(bPointer);
			}
			aPointer--;
			bPointer--;
		}

		double rate = similarity * 1.0 / Math.min(tokenA.size(), tokenB.size());
		if (rate >= SIMILAR_THRESHOLD)
			addStringToDictionary(tokenACopy, tokenBCopy, nameA, nameB);
		return rate;
	}

	private void addToNamingPairDictionary(ArrayList<NameToken> tokenA,
			ArrayList<NameToken> tokenB, TreeSet<Integer> sameTokensA,
			TreeSet<Integer> sameTokensB, String nameA, String nameB) {
		int lenA = tokenA.size();
		int lenB = tokenB.size();
		for (int i = 0, j = 0; i < lenA && j < lenB; i++, j++) {
			int firstA = sameTokensA.first();
			int firstB = sameTokensB.first();
			sameTokensA.remove(firstA);
			sameTokensB.remove(firstB);
			if (i < firstA && j < firstB) {
				String tmpA = "";
				String tmpB = "";
				for (; i < firstA; i++)
					tmpA += tokenA.get(i).getToken();
				for (; j < firstB; j++)
					tmpB += tokenB.get(i).getToken();
				addStringToMap(tmpA, tmpB, nameA, nameB);
			}
		}
	}

	private void calFieldDistance(ArrayList<NameToken> tokenA,
			ArrayList<NameToken> tokenB, String nameA, String nameB) {
		int lenA = tokenA.size();
		int lenB = tokenB.size();
		if (lenA == 1 || lenB == 1)
			return;

		if (tokenA.get(0).getToken().equals(tokenB.get(0).getToken())
				|| (tokenA.get(lenA - 1).getToken().equals(tokenB.get(lenB - 1)
						.getToken()))) {
			addStringToDictionary(tokenA, tokenB, nameA, nameB);
		}
	}

	private void detectNounInconsistent() {
		
	}

	
	private void addStringToDictionary(ArrayList<NameToken> tokenA,
			ArrayList<NameToken> tokenB, String nameA, String nameB) {
		String tokenAS = "";
		String tokenBS = "";
		for (NameToken nt : tokenA) {

			tokenAS += nt.getToken();
		}
		for (NameToken nt : tokenB) {

			tokenBS += nt.getToken();
		}
		addStringToMap(tokenAS, tokenBS, nameA, nameB);
	}

	private void addStringToMap(String tmpA, String tmpB, String nameA,
			String nameB) {

		TokenPairModel pair = new TokenPairModel(tmpA, tmpB, nameA, nameB);
		if (namePairModel.containsKey(pair)) {
			namePairModel.put(pair, namePairModel.get(pair) + 1);
		} else {
			namePairModel.put(pair, 1);
		}
	}

	public void setSimilarThreshold(double threshold) {
		SIMILAR_THRESHOLD = threshold;
	}
}
