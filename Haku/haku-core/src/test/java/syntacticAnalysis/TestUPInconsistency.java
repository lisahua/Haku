package syntacticAnalysis;

import org.testng.annotations.Test;

import seal.haku.syntacticAnalyser.usagePattern.inconsistency.UPInconsistencyAggregator;
import seal.haku.syntacticAnalyser.usagePattern.inconsistency.UPInconsistencyCluster;
import seal.haku.syntacticAnalyser.usagePattern.inconsistency.UPInconsistentProcessor;

/**
 * @Author Lisa
 * @Date: Jan 19, 2015
 */
public class TestUPInconsistency {
	// @Test
	public void testUPInconsistency() {
		UPInconsistentProcessor processor = new UPInconsistentProcessor(
				"src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-up.txt");
		processor
				.readUsagePatternFile("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-bug.txt");
	}

	// @Test
	public void testUPInconsistencyDetector() {
		UPInconsistencyAggregator icDetector = new UPInconsistencyAggregator(
				"src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-name.txt",
				"src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-method.txt");
		icDetector
				.readUsagePatternFile("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-up.txt");
	}

	@Test
	public void testUPInconsistencyCluster() {
		UPInconsistencyCluster cluster = new UPInconsistencyCluster(
				"src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-upAnomaly.txt");
		cluster.readUsagePatternFile("src/test/resources/seal/haku/output/lexicalAnalysis/elasticsearch-1.0.0-method.txt");
//	cluster.processMethod("ConcurrentMap<ShardId.put,failedShards:5,cachedStores:2,cachedShardsState:1,");
	}

}
