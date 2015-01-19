package lexicalAnalyser;

import java.util.ArrayList;

import seal.haku.lexicalAnalyser.model.IdentifierNode;
import seal.haku.lexicalAnalyser.processing.BasicNameExtractor;
import seal.haku.lexicalAnalyser.tokenizer.CamelCaseSplitter;
import seal.haku.lexicalAnalyser.tokenizer.POSTagger;

public class GenericTestUtility {
	protected BasicNameExtractor nameExtractor = new BasicNameExtractor();
	protected CamelCaseSplitter nameSplitter = CamelCaseSplitter.getInstance();
	protected POSTagger tagger = POSTagger.getInstance();

	// @Test

	public ArrayList<IdentifierNode> generateNodesDir(String dir) {
		ArrayList<IdentifierNode> nodeSet = nameExtractor.getNameInDir(dir);
		// nodeSet = nameSplitter.executeNames(nodeSet);
		// nodeSet = tagger.executeNames(nodeSet);
		return nodeSet;
	}

	protected void printNodes(ArrayList<IdentifierNode> nodeSet) {
		for (IdentifierNode tnode : nodeSet) {
			System.out.println(tnode.getTokenString());
			for (IdentifierNode fnode : tnode.getChildren2())
				System.out.println("\t" + fnode.getTokenString());
			for (IdentifierNode mnode : tnode.getChildren()) {
				System.out.println("\t" + mnode.getTokenString());
				for (IdentifierNode vnode : mnode.getChildren())
					System.out.println("\t\t" + vnode.getTokenString());
			}
		}
	}

}
