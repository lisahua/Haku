package seal.haku.lexicalAnalyser.extractName;

import org.eclipse.jdt.core.dom.ASTVisitor;

public abstract class PoorNameDetector extends ASTVisitor {

	protected String filePath;

	public PoorNameDetector setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
}
