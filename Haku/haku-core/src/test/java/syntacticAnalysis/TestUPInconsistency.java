package syntacticAnalysis;

import org.testng.annotations.Test;

import seal.haku.syntacticAnalyser.usagePattern.inconsistency.UPInconsistencyAggregator;
import seal.haku.syntacticAnalyser.usagePattern.inconsistency.UPInconsistentProcessor;

/**
 * @Author Lisa
 * @Date: Jan 19, 2015
 */
public class TestUPInconsistency {
	//@Test
	public void testUPInconsistency() {
		UPInconsistentProcessor processor = new UPInconsistentProcessor("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-up.txt");
		processor
				.readUsagePatternFile("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-bug.txt");
	}
	@Test
	public void testUPInconsistencyDetector() {
		UPInconsistencyAggregator icDetector = new UPInconsistencyAggregator("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-name.txt","src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-method.txt");
		icDetector.readUsagePatternFile("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-up.txt");
	}
}
