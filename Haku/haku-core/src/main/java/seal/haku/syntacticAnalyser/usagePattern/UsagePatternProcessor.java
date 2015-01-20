package seal.haku.syntacticAnalyser.usagePattern;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UsagePatternProcessor {
	PrintWriter writer;

	public UsagePatternProcessor() {
	}

	public UsagePatternProcessor(String outputPath) {
		try {
			writer = new PrintWriter(outputPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

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
			UsagePatternClassProcessor classProcessor = new UsagePatternClassProcessor();
			cu.accept(classProcessor);
			saveBugs(classProcessor.getUsagePatterns(),file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodeSet;
	}

	public void saveBugs(ArrayList<String> bugs,String filePath) {
		if (bugs==null || bugs.size()==0) return;
		writer.println(filePath);
		for (String bug : bugs)
			writer.println(bug);
	}
}
