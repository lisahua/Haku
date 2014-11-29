package seal.haku.lecicalAnalyser.extractName;

import java.util.ArrayList;

import org.testng.annotations.Test;

import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;
import seal.haku.sytacticAnalyser.inconsistIdentifier.MethodNameInconsistentIdentifier;
import seal.haku.sytacticAnalyser.inconsistIdentifier.VariableInconsistentIdentifier;


public class TestNameInconsistency extends GenericTestUtility{
//	private static Logger log = LoggerFactory
//            .getLogger(TestNameInconsistency.class);
	@Test
	public void testEx1() {
		System.out.println("Test Ex1: name inconsistency \n");
		// "src/test/resources/seal/haku/extractName/AtomicStats.java"
		ArrayList<IdentifierNode> nodeSet = generateNodes("src/test/resources/seal/haku/extractName/GiraphConfigurationValidator.java");
		printNodes(nodeSet);
		VariableInconsistentIdentifier vIdentifier = new VariableInconsistentIdentifier();
		for (IdentifierNode tNode : nodeSet) {
			vIdentifier.identifyAbnormal(tNode);
		}
		
	}
	@Test
	public void testEx2() {
		System.out.println("Test Ex2: name inconsistency \n");
		// "src/test/resources/seal/haku/extractName/AtomicStats.java"
		ArrayList<IdentifierNode> nodeSet = generateNodes("src/test/resources/seal/haku/extractName/MoveDeleteHookExample1.java");
		VariableInconsistentIdentifier vIdentifier = new VariableInconsistentIdentifier();
		for (IdentifierNode tNode : nodeSet) {
			vIdentifier. identifyAbnormal(tNode);
		}
	}
	
	@Test
	public void testEx4() {
		System.out.println("Test Ex1: method inconsistency \n");
		// "src/test/resources/seal/haku/extractName/AtomicStats.java"
		ArrayList<IdentifierNode> nodeSet = generateNodes("src/test/resources/seal/haku/extractName/AtomicStats.java");
		MethodNameInconsistentIdentifier mInconsistent = new MethodNameInconsistentIdentifier();
		for (IdentifierNode tnode : nodeSet) {
			mInconsistent.identifyAbnormal(tnode);;
		}
	}
}
