package seal.haku.lecicalAnalyser.extractName;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.testng.annotations.Test;

import seal.haku.lexicalAnalyser.extractName.NameExtractor;
import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;
import seal.haku.lexicalAnalyser.tokenizer.CamelCaseSplitter;
import seal.haku.lexicalAnalyser.tokenizer.POSTagger;
import ch.uzh.ifi.seal.changedistiller.ChangeDistiller;
import ch.uzh.ifi.seal.changedistiller.ChangeDistiller.Language;
import ch.uzh.ifi.seal.changedistiller.distilling.FileDistiller;

public class GenericTestUtility {
	protected NameExtractor nameExtractor = new NameExtractor();
	protected CamelCaseSplitter nameSplitter = CamelCaseSplitter.getInstance();
	protected POSTagger tagger = POSTagger.getInstance();

	// @Test
	public void testASTDistiller() {
		File left = new File(
				"src/test/resources/seal/haku/extractName/GiraphConfigurationValidator.java");
		File right = new File(
				"src/test/resources/seal/haku/extractName/GiraphConfigurationValidator2.java");

		FileDistiller distiller = ChangeDistiller
				.createFileDistiller(Language.JAVA);
		try {
			distiller.extractClassifiedSourceCodeChanges(left, right);
		} catch (Exception e) {
			/*
			 * An exception most likely indicates a bug in ChangeDistiller.
			 * Please file a bug report at
			 * https://bitbucket.org/sealuzh/tools-changedistiller/issues and
			 * attach the full stack trace along with the two files that you
			 * tried to distill.
			 */
			System.err.println("Warning: error while change distilling. "
					+ e.getMessage());
		}

		// List<SourceCodeChange> changes = distiller.getSourceCodeChanges();

	}

	protected ArrayList<IdentifierNode> generateNodes(String path) {
		ArrayList<IdentifierNode> nodeSet = nameExtractor.getNameInFile(path);
//		nodeSet = nameSplitter.executeNames(nodeSet);
//		nodeSet = tagger.executeNames(nodeSet);
		return nodeSet;
	}

	protected void printNodes(ArrayList<IdentifierNode> nodeSet) {
//		for (IdentifierNode tnode : nodeSet) {
//			System.out.println(tnode.getTokenString());
//			for (IdentifierNode fnode : tnode.getChildren2())
//				System.out.println("\t" + fnode.getTokenString());
//			for (IdentifierNode mnode : tnode.getChildren()) {
//				System.out.println("\t" + mnode.getTokenString());
//				for (IdentifierNode vnode : mnode.getChildren())
//					System.out.println("\t\t" + vnode.getTokenString());
//			}
//		}
	}
	@Test
	public void testPrint() {

		printNodes("/Users/jinruhua/Documents/Study/2014/nameExample/snapshots/elasticsearch-0.19.0/src", "/Users/jinruhua/Documents/Study/2014/nameExample/snapshots/elasticsearch-0.19.0.txt");
		
	}
	protected void printNodes(String input,String path) {
		ArrayList<IdentifierNode> nodeSet=	nameExtractor.getNameInDir(input);
		PrintWriter writer;
		
		try {
			writer = new PrintWriter(path);
//			for (IdentifierNode tnode : nodeSet) {
//				writer.println(tnode.getTokenString());
//				for (IdentifierNode fnode : tnode.getChildren2())
//					writer.println("," + fnode.getTokenString());
//				for (IdentifierNode mnode : tnode.getChildren()) {
//					writer.println(",," + mnode.getTokenString());
//					for (IdentifierNode vnode : mnode.getChildren())
//						writer.println(",,," + vnode.getTokenString());
//				}
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
