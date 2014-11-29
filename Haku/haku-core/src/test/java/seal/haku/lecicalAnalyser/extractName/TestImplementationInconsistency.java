package seal.haku.lecicalAnalyser.extractName;

import java.util.ArrayList;

import org.testng.annotations.Test;

import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;
import seal.haku.sytacticAnalyser.inconsistIdentifier.ImplementationInconsistentIdentifier;

public class TestImplementationInconsistency extends GenericTestUtility {
//	private static Logger log = LoggerFactory
//            .getLogger(TestImplementationInconsistency.class);
	@Test
	public void testEx5() {
		System.out.println("Test Ex5: implementation SET inconsistency \n");
		// "src/test/resources/seal/haku/extractName/AtomicStats.java"
		ArrayList<IdentifierNode> nodeSet = generateNodes("src/test/resources/seal/haku/extractName/DefaultErrorHandlerBuilder.java");
//		printNodes(nodeSet);
		ImplementationInconsistentIdentifier vIdentifier = new ImplementationInconsistentIdentifier();
		for (IdentifierNode tNode : nodeSet) {
			vIdentifier.identifyAbnormal(tNode);
		}

	}
	@Test
	public void testEx7() {
		System.out.println("Test Ex7: implementation IS inconsistency \n");
		// "src/test/resources/seal/haku/extractName/AtomicStats.java"
		ArrayList<IdentifierNode> nodeSet = generateNodes("src/test/resources/seal/haku/extractName/CharRange.java");
//		printNodes(nodeSet);
		ImplementationInconsistentIdentifier vIdentifier = new ImplementationInconsistentIdentifier();
		for (IdentifierNode tNode : nodeSet) {
			vIdentifier.identifyAbnormal(tNode);
		}

	}
	@Test
	public void testEx10() {
		System.out.println("Test Ex10: RETURN BOOLEAN inconsistency \n");
		// "src/test/resources/seal/haku/extractName/AtomicStats.java"
		ArrayList<IdentifierNode> nodeSet = generateNodes("src/test/resources/seal/haku/extractName/ServiceInstanceBuilder.java");
//		printNodes(nodeSet);
		ImplementationInconsistentIdentifier vIdentifier = new ImplementationInconsistentIdentifier();
		for (IdentifierNode tNode : nodeSet) {
			vIdentifier.identifyAbnormal(tNode);
		}

	}

}
