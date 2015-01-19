package seal.haku.lexicalAnalyser.similarity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import seal.haku.lexicalAnalyser.model.IdentifierNode;

/**
 * @Author Lisa
 * @Date: Jan 17, 2015
 */
public class ApproximatePurityProcessor {

	public void getNameInDir(String dirPath) {
		File folder = new File(dirPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].getName().endsWith(".java")) {
				getNameInFile(listOfFiles[i]);
			} else if (listOfFiles[i].isDirectory()) {
				getNameInDir(listOfFiles[i].getAbsolutePath());
			}
		}
	}

	public ArrayList<IdentifierNode> getNameInFile(File file) {
		ArrayList<IdentifierNode> nodeSet = new ArrayList<IdentifierNode>();
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		String fileString = null;
		try {
			fileString = FileUtils.readFileToString(file);
			parser.setSource(fileString.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
			ApproximatePurityClassProcessor classProcessor = new ApproximatePurityClassProcessor();
			cu.accept(classProcessor);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodeSet;

	}
}
