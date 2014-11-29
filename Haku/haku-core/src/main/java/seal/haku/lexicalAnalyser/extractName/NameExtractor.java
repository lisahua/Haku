package seal.haku.lexicalAnalyser.extractName;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;

public class NameExtractor {
	ArrayList<IdentifierNode> nodeSet = new ArrayList<IdentifierNode>();
	PrintWriter writer;

	public NameExtractor() {

	}

	public NameExtractor(String output) {
		try {
			writer = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<IdentifierNode> getNameInDir(String dirPath) {
		File folder = new File(dirPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].getName().endsWith(".java")) {
				// System.out.println(listOfFiles[i].getName().substring(0,
				// listOfFiles[i].getName().length() - 5));
				getNameInFile(listOfFiles[i]);
			} else if (listOfFiles[i].isDirectory()) {
				getNameInDir(listOfFiles[i].getAbsolutePath());
			}
		}
		return nodeSet;
	}

	public ArrayList<IdentifierNode> getNameInFile(File file) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		String fileString = null;
		try {
			fileString = FileUtils.readFileToString(file);
			parser.setSource(fileString.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
			writer.println(file.getPath().substring(80));
			TypeASTVisitor mdVisitor = new TypeASTVisitor();
			cu.accept(mdVisitor);
			// List<IdentifierNode> typeList = mdVisitor.getTypeList();
			// for (IdentifierNode node : typeList) {
			// nodeSet.add(node);
			// }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodeSet;

	}

	public ArrayList<IdentifierNode> getNameInFile(String file) {
		return getNameInFile(new File(file));
	}

	class TypeASTVisitor extends ASTVisitor {
		public boolean visit(TypeDeclaration td) {
			MtdASTVisitor mtdVisitor = new MtdASTVisitor();
			td.accept(mtdVisitor);
			for (String s : mtdVisitor.getMethods()) {
				writer.println("," + s);
			}
			return true;
		}
	}

	class MtdASTVisitor extends ASTVisitor {
		HashSet<String> methods = new HashSet<String>();

		public boolean visit(MethodDeclaration decleration) {
			methods.add(decleration.getName().toString());
			return true;
		}

		public HashSet<String> getMethods() {
			return methods;
		}
	}
}
