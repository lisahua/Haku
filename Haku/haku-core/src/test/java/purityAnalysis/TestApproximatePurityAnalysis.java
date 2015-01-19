package purityAnalysis;

import org.testng.annotations.Test;

import seal.haku.lexicalAnalyser.similarity.ApproximatePurityProcessor;

public class TestApproximatePurityAnalysis {
	@Test
	public void testVisitor() {
		ApproximatePurityProcessor processor = new ApproximatePurityProcessor("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-bug.txt");
		processor
				.getNameInDir("/Users/admin/Documents/snapshot/elasticsearch-1.0.0");
	}
}
