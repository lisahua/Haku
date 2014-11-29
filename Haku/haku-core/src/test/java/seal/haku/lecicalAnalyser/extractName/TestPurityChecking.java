package seal.haku.lecicalAnalyser.extractName;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import seal.haku.dependencyInjection.RequestObject;
import seal.haku.lexicalAnalyser.extractName.FlattenASTExtractor;
import seal.haku.lexicalAnalyser.extractName.NameExtractor;
import seal.haku.lexicalAnalyser.extractName.StatementAnalyser;
import seal.haku.lexicalAnalyser.model.DBEntity;
import seal.haku.lexicalAnalyser.model.VerbDictionary;
import seal.haku.lexicalAnalyser.tokenizer.POSTagger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IPointer;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class TestPurityChecking {
	// @Test
	public void checkAbbreviation() {
		Assert.assertTrue(VerbDictionary.isAbbreviation("str", "String"));
		Assert.assertTrue(VerbDictionary.isAbbreviation("inc", "increase"));
		Assert.assertTrue(VerbDictionary.isAbbreviation("dec", "decrease"));
		Assert.assertTrue(VerbDictionary.isAbbreviation("pt", "Point"));
		Assert.assertTrue(VerbDictionary.isAbbreviation("sel",
				"StructureSelection"));
	}

	// @Test
	public void checkWordNet() {
		String WORDNETPATH = "src/main/resources/seal/haku/wordnet/dict";
		URL url;
		try {
			url = new URL("file", null, WORDNETPATH);
			IDictionary dict = new Dictionary(url);
			dict.open();

			IIndexWord vWord = dict.getIndexWord("increase", POS.VERB);

			for (IWordID wID : vWord.getWordIDs()) {
				IWord word = dict.getWord(wID);
				Map<IPointer, List<IWordID>> relatedMap = word.getRelatedMap();
				// for (Map.Entry<IPointer, List<IWordID>> entry : relatedMap
				// .entrySet()) {
				// for (IWordID wordID : entry.getValue())
				// System.out.println(entry.getKey() + "," +
				// dict.getWord(wordID).getLemma());
				// }
				List<IWordID> relatedWord = word
						.getRelatedWords(Pointer.ANTONYM);
				for (IWordID relatedID : relatedWord)
					System.out.println("antonym " + dict.getWord(relatedID));
				// relatedWord = word.getRelatedWords(Pointer.SIMILAR_TO);
				// for (IWordID relatedID : relatedWord)
				// System.out.println("similar to " + dict.getWord(relatedID));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// @Test
	public void testMethodInvokePurity() {
//		DBEntity.setOutputPath("elasticsearch-100-usage2.csv");
		FlattenASTExtractor extractor = new FlattenASTExtractor();
		String dir = "/Users/jinruhua/Documents/Study/2014/nameExample/snapshots/jedit-1.0.1";
		extractor.getFileDir(dir);

//		DBEntity.setOutputPath("elasticsearch-0190-usage2.csv");
//		dir = "/Users/jinruhua/Documents/Study/2014/nameExample/snapshots/elasticsearch-0.90.0/src/main/java/";
//		extractor.getFileDir(dir);
//
//		StatementAnalyser analyser = new StatementAnalyser();
//		analyser.parseFieldStatement(
//				"/Users/jinruhua/Documents/Study/2014/workspace/Haku-git/Haku/elasticsearch-100-usage2.csv",
//				"elasticsearch-100-impure2.csv");
//		analyser.parseFieldStatement(
//				"/Users/jinruhua/Documents/Study/2014/workspace/Haku-git/Haku/elasticsearch-0190-usage2.csv",
//				"elasticsearch-0190-impure2.csv");

	}
//	@Test
	public void testConfigJson() throws JsonGenerationException, JsonMappingException, IOException {
		String path = "src/test/resources/seal/haku/config.json";
		ObjectMapper mapper = new ObjectMapper(); // create once, reuse
//		mapper.writeValue(new File(path), new RequestObject());
		RequestObject value = mapper.readValue(new File(path), RequestObject.class);
		System.out.println("value - checkApproach: "+value.getCheckApproach());
	}

	// @Test
	public void testPOSTagger() {

		POSTagger tagger = POSTagger.getInstance();
		System.out.println(tagger.executeSingleName("previous"));
		System.out.println(tagger.executeSingleName("next"));
		System.out.println(tagger.executeSingleName("remove"));
		System.out.println(tagger.executeSingleName("add"));

	}

	// @Test
	public void filterImpure() {
		StatementAnalyser analyser = new StatementAnalyser();
		analyser.getSameUsage();
	}

	//@Test
	public void calRelatedBug() throws Exception {
		String path1 = "elasticsearch-0190-impure.csv";
		String path2 = "elasticsearch-0190-impure2.csv";
		HashSet<String> files = new HashSet<String>();
		BufferedReader reader = new BufferedReader(new FileReader(path1));
		String line = "";
		while ((line = reader.readLine()) != null) {
			files.add(line.split(",")[0]);
		}
		reader.close();
		reader  = new BufferedReader(new FileReader(path2));
		while ((line = reader.readLine()) != null) {
			files.add(line.split(",")[0]);
		}
		System.out.println(" how many files ? "
				+ files.size());
		int sumBugs = 0;
		DBEntity.initDatabase() ;
		for (String s : files) {
			sumBugs += DBEntity.calRelatedBug(s);
		}
		System.out.println(" how many bugs is related to these files? "
				+ sumBugs);
		reader.close();
	}
	//@Test
	public void calCoefficient() throws Exception {
		String path1 = "elasticsearch-0190-impure.csv";
		String path2 = "elasticsearch-0190-impure2.csv";
		HashMap<String,Integer> files = new HashMap<String,Integer>();
		BufferedReader reader = new BufferedReader(new FileReader(path1));
		String line = "";
		while ((line = reader.readLine()) != null) {
			String file=line.split(",")[0];
			int count = (files.containsKey(file))? files.get(file)+1:1;
			files.put(file, count);
			
		}
		reader.close();
		reader  = new BufferedReader(new FileReader(path2));
		while ((line = reader.readLine()) != null) {
			String file=line.split(",")[0];
			int count = (files.containsKey(file))? files.get(file)+1:1;
			files.put(file, count);
		}
		System.out.println(" how many files ? "
				+ files.size());
//		int sumBugs = 0;
		DBEntity.initDatabase() ;
		for (Map.Entry<String, Integer> entry: files.entrySet()) {
			System.out.println(entry.getValue()+","+ DBEntity.calRelatedBug(entry.getKey()));
		}
//		System.out.println(" how many bugs is related to these files? "
//				+ sumBugs);
		reader.close();
	}
	@Test
	public void runNameExtractor() {
		String dir = "/Users/jinruhua/Documents/Study/2014/nameExample/snapshots/elasticsearch-0.19.0/src/main/java/";
		NameExtractor extractor = new NameExtractor("/Users/jinruhua/Documents/Study/2014/nameExample/workFolder/outputFiles/elasticsearch_methodAll.txt") ;
		extractor.getNameInDir(dir);
		
	}
	
	//@Test
	public void testNameExtractor() {
		String dir = "/Users/jinruhua/Documents/Study/2014/nameExample/snapshots/elasticsearch-0.19.0/src/main/java/org/elasticsearch/common/settings/loader/JsonSettingsLoader.java";
		NameExtractor extractor = new NameExtractor("/Users/jinruhua/Documents/Study/2014/nameExample/workFolder/outputFiles/elasticsearch_methodTest.txt") ;
		extractor.getNameInFile(dir);
		
	}
}
