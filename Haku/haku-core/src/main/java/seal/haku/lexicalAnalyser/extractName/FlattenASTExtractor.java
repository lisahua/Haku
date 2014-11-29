package seal.haku.lexicalAnalyser.extractName;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParameterizedType;

import seal.haku.dependencyInjection.ConfigMaintainer;
import seal.haku.lexicalAnalyser.model.nameNode.IdentifierNode;

public class FlattenASTExtractor {

	public void getFileDir(String dirPath) {
		File folder = new File(dirPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].getName().endsWith(".java")) {
				// System.out.println(listOfFiles[i].getName().substring(0,
				// listOfFiles[i].getName().length() - 5));
				getNameInFile(listOfFiles[i]);
			} else if (listOfFiles[i].isDirectory()) {
				getFileDir(listOfFiles[i].getAbsolutePath());
			}
		}
	}

	public void getNameInFile(File file) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		String fileString = null;
		try {
			fileString = FileUtils.readFileToString(file);
			parser.setSource(fileString.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

			PoorNameDetector cVisitor = ConfigMaintainer.getCheckApproach().setFilePath(file
					.getAbsolutePath().substring(94));
//			SlicingASTVisitor cVisitor = new SlicingASTVisitor(file
//					.getAbsolutePath().substring(94));
			// PurityASTVisitor cVisitor = new
			// PurityASTVisitor(file.getAbsolutePath().substring(94));
			cu.accept(cVisitor);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getNameInFile(String filePath) {
		getNameInFile(new File(filePath));
	}

	private HashSet<IdentifierNode> checkPurity(MethodDeclaration mtd,
			HashSet<FieldDeclaration> flds) {
		HashSet<IdentifierNode> fields = new HashSet<IdentifierNode>();
		MethodASTVisitor mVisitor = new MethodASTVisitor();
		mtd.accept(mVisitor);

		return fields;
	}

}

class ClassASTVisitor extends ASTVisitor {
	HashSet<MethodDeclaration> methodList = new HashSet<MethodDeclaration>();
	HashSet<FieldDeclaration> fieldList = new HashSet<FieldDeclaration>();

	public boolean visit(MethodDeclaration decleration) {
		methodList.add(decleration);
		return true;
	}

	public boolean visit(FieldDeclaration decleration) {
		fieldList.add(decleration);
		return true;
	}

	public HashSet<MethodDeclaration> getMethodList() {
		return methodList;
	}

	public HashSet<FieldDeclaration> getFieldList() {
		return fieldList;
	}
}

class MethodASTVisitor extends ASTVisitor {
	public boolean visit(Assignment node) {
		return true;
	}

	public boolean visit(ExpressionStatement node) {
		return true;
	}

	public boolean visit(FieldAccess node) {

		return true;
	}

	public boolean visit(MethodInvocation node) {
		return true;
	}

	public boolean visit(ParameterizedType node) {
		return true;
	}

}