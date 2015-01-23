package syntacticAnalysis;

import org.testng.annotations.Test;

import seal.haku.syntacticAnalyser.purityAnalysis.PurityAnalysisProcessor;
import seal.haku.syntacticAnalyser.purityAnalysis.PurityVerbRatioCalculator;

/**
 * @Author Lisa
 * @Date: Jan 19, 2015
 */
public class TestPurityAnalysis {
	@Test
	public void testPurityRatio() {
		PurityVerbRatioCalculator ratioCal = new PurityVerbRatioCalculator();
		ratioCal.readFile("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-bug.txt");
		
		PurityAnalysisProcessor processor = new PurityAnalysisProcessor("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-purity.txt",ratioCal.calculateRatio());
		processor.readFile("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-bug.txt");
		processor.printPureVerbs();
	}
	//@Test
	public void testPurityProcessor() {
		PurityAnalysisProcessor processor = new PurityAnalysisProcessor("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-purity.txt");
		processor.readFile("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-bug.txt");
		
	}
	
	
}
