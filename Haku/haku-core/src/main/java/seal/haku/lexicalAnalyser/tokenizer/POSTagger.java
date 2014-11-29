package seal.haku.lexicalAnalyser.tokenizer;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;
import seal.haku.lexicalAnalyser.model.nameNode.NameToken;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class POSTagger {

	private static POSTagger posTagger = new POSTagger();
	private static final String WORDNETPATH = "src/main/resources/seal/haku/wordnet/dict";
	private final static String NLPTAGGER = "src/main/resources/seal/haku/POSTaggerModel/english-left3words-distsim.tagger";

	private static IDictionary dict;
	private static MaxentTagger tagger;
	private static HashSet<String> verbSet = new HashSet<String>();
	private static HashSet<String> nounSet = new HashSet<String>();

	private POSTagger() {
		

	}

	private static void initLocalSet() {
		Iterator<IIndexWord> wordIterator = dict.getIndexWordIterator(POS.VERB);
		while (wordIterator.hasNext()) {
			verbSet.add(wordIterator.next().getLemma());
		}
		wordIterator = dict.getIndexWordIterator(POS.NOUN);
		while (wordIterator.hasNext()) {
			nounSet.add(wordIterator.next().getLemma());
		}

	}


	public static POSTagger getInstance() {
		if (tagger!=null) return posTagger;
		try {
			tagger = new MaxentTagger(NLPTAGGER);
			URL url = new URL("file", null, WORDNETPATH);
			dict = new Dictionary(url);
			dict.open();
			initLocalSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posTagger;
	}



	public String executeSingleName(String token) {
			token = tagger.tagString(token);
		return token;
	}

	public NameToken[] executeSingleName(String[] tokens) {
		NameToken[] ntList = new NameToken[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			ntList[i] = new NameToken(executeSingleName(tokens[i]));
		}
		return ntList;
	}

	public static boolean[] isWord(String[] words) {
		int wordLen = words.length;
		boolean[] results = new boolean[wordLen];

		switch (wordLen) {
		case 1:
			results[0] = verbSet.contains(words[0])
					|| nounSet.contains(words[0]);

			break;
		case 2:
			results[0] = verbSet.contains(words[0]);
			results[1] = nounSet.contains(words[1]);
			break;
		}
		return results;
	}

	public static HashSet<String> getAntonymVerb(String verb) {
		HashSet<String> antonyms = new HashSet<String>();
		IIndexWord vWord = dict.getIndexWord(verb, POS.VERB);
		for (IWordID wID : vWord.getWordIDs()) {
			IWord word = dict.getWord(wID);
			List<IWordID> relatedWord = word.getRelatedWords(Pointer.ANTONYM);
			for (IWordID id : relatedWord)
				antonyms.add(dict.getWord(id).getLemma());
		}
		return antonyms;
	}
}
