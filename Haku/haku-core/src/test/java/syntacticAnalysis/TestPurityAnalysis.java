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
		ratioCal.readUsagePatternFile("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-bug.txt");
		ratioCal.calculateRatio();
	}
	@Test
	public void testPurityProcessor() {
		PurityAnalysisProcessor processor = new PurityAnalysisProcessor("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-purity.txt");
		processor.readUsagePatternFile("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-bug.txt");
		
	}
}
