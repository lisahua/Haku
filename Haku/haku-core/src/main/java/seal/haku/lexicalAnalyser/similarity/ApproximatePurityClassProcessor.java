package seal.haku.lexicalAnalyser.similarity;

import java.util.HashSet;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * @Author Lisa
 * @Date: Jan 18, 2015
 */
public class ApproximatePurityClassProcessor extends ASTVisitor {

	HashSet<String> methodList = new HashSet<String>();
	HashSet<String> fieldList = new HashSet<String>();

	public boolean visit(TypeDeclaration declaration) {
		ApproximatePurityClassVisitor cVisitor = new ApproximatePurityClassVisitor();
		declaration.accept(cVisitor);
		return true;
	}

}
