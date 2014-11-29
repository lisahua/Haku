package seal.haku.lexicalAnalyser.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class VerbDictionary {
	private static String[] primitiveType = { "byte", "short", "int", "long",
			"float", "double", "boolean", "bool", "char", "Character",
			"Integer", "String" };
	private static HashSet<String> primitiveTypeSet = new HashSet<String>(
			Arrays.asList(primitiveType));
	private static HashMap<String, Integer> dic = new HashMap<String, Integer>();
	private static HashSet<String> verbCache = new HashSet<String>();

	public static void addVerbCache(String verb) {
		verbCache.add(verb);
	}

	public static HashSet<String> getVerbCache() {
		return verbCache;
	}

	public static boolean containsVerb(String verb) {
		return verbCache.contains(verb);
	}

	public static void addVerb(String verb, String type) {
		int count = 0;
		if (dic.containsKey(verb))
			count = dic.get(verb);
		dic.put(verb, count + 1);
	}

	public static void generateVerbNode() {

	}

	public static boolean isAbbreviation(String abbr, String full) {
		char[] abbrArray = abbr.toLowerCase().toCharArray();
		char[] fullArray = full.toLowerCase().toCharArray();
		int j = 0;
		for (int i = 0; i < abbrArray.length; i++) {
			while (j < fullArray.length) {
				if (abbrArray[i] == fullArray[j]) {
					break;
				} else
					j++;
			}
			if (j == fullArray.length - 1 && i != abbrArray.length - 1)
				return false;
		}
		return true;
	}

}
