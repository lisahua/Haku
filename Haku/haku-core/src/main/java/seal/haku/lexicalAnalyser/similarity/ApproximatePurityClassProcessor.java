package seal.haku.lexicalAnalyser.similarity;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * @Author Lisa
 * @Date: Jan 18, 2015
 */
public class ApproximatePurityClassProcessor extends ASTVisitor {
	HashSet<String> bugs = new HashSet<String>();

	public boolean visit(TypeDeclaration declaration) {
		ApproximatePurityClassVisitor cVisitor = new ApproximatePurityClassVisitor();
		declaration.accept(cVisitor);
		bugs.addAll(cVisitor.getBugs());
		return true;
	}

	public HashSet<String> getBugs() {
		return bugs;
	}
}
