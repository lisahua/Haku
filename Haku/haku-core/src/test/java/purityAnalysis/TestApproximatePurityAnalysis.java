package purityAnalysis;

import org.testng.annotations.Test;

import seal.haku.lexicalAnalyser.similarity.ApproximatePurityProcessor;

public class TestApproximatePurityAnalysis {
	@Test
	public void testVisitor() {
		ApproximatePurityProcessor processor = new ApproximatePurityProcessor();
		processor
				.getNameInDir("/Users/admin/Documents/snapshot/elasticsearch-1.0.0");
	}
}
