package syntacticAnalysis;

import org.testng.annotations.Test;

import seal.haku.syntacticAnalyser.usagePattern.UsagePatternProcessor;

public class TestApproximatePurityAnalysis {
	@Test
	public void testVisitor() {
		UsagePatternProcessor processor = new UsagePatternProcessor("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-bug.txt");
		processor
				.getNameInDir("/Users/admin/Documents/snapshot/elasticsearch-1.0.0");
	}
}
