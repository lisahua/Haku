package seal.haku.lexicalAnalyser.processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import seal.haku.lexicalAnalyser.model.IdentifierNode;

public class SimpleNamePrinter {
	ArrayList<IdentifierNode> nodeSet = new ArrayList<IdentifierNode>();
	PrintWriter writer;
	SimpleNamePrinter printer;

	public SimpleNamePrinter(String outputPath) {
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

	public void getNameInFile(File file) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		String fileString = null;
		try {
			fileString = FileUtils.readFileToString(file);
			writer.println(file.getAbsolutePath());
			parser.setSource(fileString.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

			NameASTVisitor mdVisitor = new NameASTVisitor();
			cu.accept(mdVisitor);
			List<IdentifierNode> typeList = mdVisitor.getTypeList();
			for (IdentifierNode node : typeList) {
				// nodeSet.add(node);
				writer.println("," + node.getName());
				for (IdentifierNode mNode : node.getChildren()) {
					writer.println(",," + mNode.getName());
					for (IdentifierNode vNode: mNode.getChildren())
						writer.println(",,," + vNode.getName());
				}
				for (IdentifierNode fNode : node.getChildren2()) {
					writer.println(",," + fNode.getName());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void printNodes(ArrayList<IdentifierNode> nodeSet) {

	}
}
